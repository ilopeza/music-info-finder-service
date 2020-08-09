package com.musicinfofinder.musicinfofinderservice.services;

import com.musicinfofinder.musicinfofinderservice.exceptions.ExtractionLyricsException;
import com.musicinfofinder.musicinfofinderservice.exceptions.NoLyricsFoundException;
import com.musicinfofinder.musicinfofinderservice.models.response.search.genius.GeniusResult;
import com.musicinfofinder.musicinfofinderservice.models.response.search.genius.IResult;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Slf4j
@Service
public class GeniusExtractionService implements ExtractionLyricsService {

    @Override
    public String extractLyrics(IResult result) {
        if (isNull(result)) {
            log.error("No lyrics can be extracted from empty response");
            throw new NoLyricsFoundException("No lyrics can be extracted from empty response");
        }

        GeniusResult geniusResult = (GeniusResult) result;
        String geniusLyricsUrl = geniusResult.getUrl();
        if (isEmpty(geniusLyricsUrl)) {
            log.error("No url was found in response {}", geniusResult);
            throw new NoLyricsFoundException("No url was found");
        }
        Document document;
        try {
            document = Jsoup.connect(geniusLyricsUrl).get();
        } catch (IOException ioException) {
            log.error("Could not extract lyrics from response {}", geniusResult);
            throw new ExtractionLyricsException("Could not extract lyrics", ioException);
        }
        String lyrics;
        Elements lyricsDiv = document.select(".lyrics");
        if (lyricsDiv.isEmpty()) {
            log.error("Missing div container for lyrics");
            throw new ExtractionLyricsException("Missing container for lyrics");
        }

        lyrics = Jsoup.clean(lyricsDiv.html(), Whitelist.none().addTags("br"));
        return lyrics.replace("<br>", "").trim();
    }
}
