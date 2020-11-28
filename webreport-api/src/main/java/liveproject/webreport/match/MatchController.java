package liveproject.webreport.match;

import liveproject.webreport.season.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("season-report/{season}")
    @ResponseStatus(value = HttpStatus.OK)
    public Season seasonReport(@PathVariable("season") String season, Model model) {
        return matchService.aggregateSeason(season);
    }

    @GetMapping("matches-report/{season}")
    @ResponseStatus(value = HttpStatus.OK)
    public Set<Match> matchesReport(@PathVariable("season") String season, Model model) {
        return matchService.getAllBySeasonSorted(season);
    }

    @PostMapping("/match/{season}")
    public Integer addMatches(@PathVariable String season, @RequestBody List<Match> matches, Model model) {
        Map<String, Integer> counts = matchService.saveAll(season, matches);
        int totalCount = counts.values().stream()
                .mapToInt(i -> i) // this unboxes
                .sum();
        return totalCount;
    }
}
