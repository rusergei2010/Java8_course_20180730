package b_streams.songs;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Task7 {
    public static void main(String[] args) {

        Song someWindySong = new Song();

        //---------------------------------

        UnaryOperator<Song> songImprovement = (song) -> {
            Song.Lyrics betterLyrics = new Song.Lyrics(
                    song.getLyrics().lines().stream()
                            .flatMap(line -> Stream.of(line, line))
                            .collect(toList())
            );
            return song.withLyrics(betterLyrics);
        };

        songImprovement.apply(someWindySong);

        //---------------------------------


    }

    private static class Song {
        private Lyrics lyrics;

        public Lyrics getLyrics() {
            return lyrics;
        }

        public void setLyrics(Lyrics lyrics) {
            this.lyrics = lyrics;
        }

        public Song withLyrics(Lyrics lyrics) {
            return new Song();
        }

        private static class Lyrics {
            public Lyrics(List<String> lines) {

            }

            public List<String> lines() {
                return null;
            }
        }
    }
}
