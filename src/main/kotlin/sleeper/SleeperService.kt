package io.zkz.fantasyfootball.sleeper

import io.zkz.fantasyfootball.IService
import io.zkz.fantasyfootball.ManagerService
import io.zkz.fantasyfootball.sleeper.models.Draft
import io.zkz.fantasyfootball.sleeper.models.DraftPick
import kotlinx.coroutines.runBlocking

object SleeperService : IService {
    private val api = SleeperApi()

    private lateinit var thread: Thread
    private var shutdownFlag = false

    private val draftLock = Object()
    private val draftPickLock = Object()
    private var draftLoading = false

    var draft: Draft? = null
        private set(value) {
            synchronized(draftLock) {
                field = value
            }
        }
        get() = synchronized(draftLock) {
            return field
        }
    var draftPicks = mapOf<Pair<Int, Int>, DraftPick>()
        private set(value) {
            val hasUpdates: Boolean

            synchronized(draftLock) {
                hasUpdates = value.hashCode() != field.hashCode()

                field = value
            }

            if (hasUpdates) {
                ManagerService.onSleeperUpdates(confirmPrint = draftLoading)
            }
        }
        get() = synchronized(draftPickLock) {
            return field
        }

    override fun initialize() {
        thread = Thread {
            while (!shutdownFlag) {
                Thread.sleep(1000)
                try {
                    fetchLatestPicks()
                } catch (e: Exception) {
                    System.err.println(e)
                }
            }
        }.apply { start() }
    }

    override fun dispose() {
        shutdownFlag = true
    }

    fun setDraftId(draftId: String) {
        draftId.toLongOrNull() ?: throw IllegalArgumentException("Draft ID '$draftId' must be a number")

        return loadDraft(draftId)
    }

    private fun loadDraft(draftId: String) {
        draftLoading = true

        draft = runBlocking { api.getDraft(draftId) }
        fetchLatestPicks()

        draftLoading = false
    }

    private fun fetchLatestPicks() {
        if (draft == null) {
            return
        }

        draftPicks = runBlocking { api.getDraftPicks(draft!!.draft_id).associateBy { Pair(it.round, it.draft_slot) } }
    }
}