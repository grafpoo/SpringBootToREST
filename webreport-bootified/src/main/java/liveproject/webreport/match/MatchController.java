package liveproject.webreport.match;

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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/reports")
public class MatchController {

    private MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("season-report/{season}")
    @ResponseStatus(value = HttpStatus.OK)
    public String account(@PathVariable("season") String season, Model model) {
        model.addAttribute("season", matchService.aggregateSeason(season));
        return "reports/SeasonReport";
    }

    @PostMapping("/match/{season}")
    public String addMatches(@PathVariable String season, @RequestBody List<Match> matches, Model model) {
        Map<String, Integer> counts = matchService.saveAll(season, matches);
        int totalCount = counts.values().stream()
                .mapToInt(i -> i) // this unboxes
                .sum();
        model.addAttribute("seasonCounts", counts);
        model.addAttribute("totalCount", totalCount);
        return "reports/LoadReport";
    }
}
