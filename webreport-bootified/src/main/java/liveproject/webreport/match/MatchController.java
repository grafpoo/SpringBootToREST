package liveproject.webreport.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/reports")
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
}
