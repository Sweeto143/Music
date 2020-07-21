/*
 * Copyright (c) 2019 Hemanth Savarala.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by
 *  the Free Software Foundation either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package io.github.muntashirakon.music.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import io.github.muntashirakon.music.EXTRA_SONG
import io.github.muntashirakon.music.R
import io.github.muntashirakon.music.extensions.colorButtons
import io.github.muntashirakon.music.extensions.materialDialog
import io.github.muntashirakon.music.model.PlaylistSong
import io.github.muntashirakon.music.util.PlaylistsUtil

class RemoveFromPlaylistDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val songs = requireArguments().getParcelableArrayList<PlaylistSong>(EXTRA_SONG)

        var title = 0
        var message: CharSequence = ""
        if (songs != null) {
            if (songs.size > 1) {
                title = R.string.remove_songs_from_playlist_title
                message = HtmlCompat.fromHtml(
                    String.format(getString(R.string.remove_x_songs_from_playlist), songs.size),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            } else {
                title = R.string.remove_song_from_playlist_title
                message = HtmlCompat.fromHtml(
                    String.format(
                        getString(R.string.remove_song_x_from_playlist),
                        songs[0].title
                    ),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            }
        }

        return materialDialog(title)
            .setMessage(message)
            .setPositiveButton(R.string.remove_action) { _, _ ->
                PlaylistsUtil.removeFromPlaylist(
                    requireContext(),
                    songs as MutableList<PlaylistSong>
                )
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
            .colorButtons()
    }

    companion object {

        fun create(song: PlaylistSong): RemoveFromPlaylistDialog {
            val list = ArrayList<PlaylistSong>()
            list.add(song)
            return create(list)
        }

        fun create(songs: ArrayList<PlaylistSong>): RemoveFromPlaylistDialog {
            val dialog = RemoveFromPlaylistDialog()
            val args = Bundle()
            args.putParcelableArrayList(EXTRA_SONG, songs)
            dialog.arguments = args
            return dialog
        }
    }
}