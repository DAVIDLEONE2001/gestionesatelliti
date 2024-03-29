package it.prova.gestionesatelliti.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatelliti.Service.SatelliteService;
import it.prova.gestionesatelliti.model.Satellite;
import it.prova.gestionesatelliti.utility.BindingResultUtils;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {

	@Autowired
	private SatelliteService satelliteService;

	@GetMapping
	public ModelAndView listAll() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> result = satelliteService.listAllElements();
		mv.addObject("satellite_list_attribute", result);
		mv.setViewName("satellite/list");
		return mv;

	}
	
	@GetMapping("/attivi2")
	public ModelAndView attiviDa2Anni() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> result = satelliteService.lanciatiDa2AnniOPiuAttivi();
		mv.addObject("satellite_list_attribute", result);
		mv.setViewName("satellite/list");
		return mv;
		
	}
	
	@GetMapping("/inattiviUp")
	public ModelAndView disattivatiInOrbita() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> result = satelliteService.disattivatiInOrbita();
		mv.addObject("satellite_list_attribute", result);
		mv.setViewName("satellite/list");
		return mv;
		
	}
	@GetMapping("/fissiUp")
	public ModelAndView attiviDa10AnniFissi() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> result = satelliteService.lanciatiDa10AnniFissi();
		mv.addObject("satellite_list_attribute", result);
		mv.setViewName("satellite/list");
		return mv;
		
	}

	@GetMapping("/search")
	public String search() {
		return "satellite/search";
	}

	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		List<Satellite> results = satelliteService.findByExample(example);
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("insert_satellite_attr", new Satellite());
		return "satellite/insert";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		result = BindingResultUtils.bindErrorsSatellite(satellite, result);

		if (result.hasErrors())
			return "satellite/insert";

		satelliteService.inserisciNuovo(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/showEdit/{idSatellite}")
	public String showEdit(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("edit_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/showEdit";
	}

	@PostMapping("/edit")
	public String edit(@Valid @ModelAttribute("edit_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		result = BindingResultUtils.bindErrorsSatellite(satellite, result);

		if (result.hasErrors())
			return "satellite/showEdit";

		satelliteService.aggiorna(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@GetMapping("/show/{idSatellite}")
	public String show(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("show_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/show";
	}

	@GetMapping("/showDelete/{idSatellite}")
	public String showDelete(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("delete_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/showDelete";
	}

	@PostMapping("/delete")
	public String delete(@Valid @ModelAttribute("delete_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		satelliteService.rimuovi(satellite.getId());
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@PostMapping("/lancia")
	public String lancia(@Valid @ModelAttribute("lancia_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		satelliteService.lanciaSatellite(satellite.getId());
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}

	@PostMapping("/ritorna")
	public String ritorna(@Valid @ModelAttribute("ritorna_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		satelliteService.ritornaSatellite(satellite.getId());
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
	@GetMapping("/emergenzaShow")
	public ModelAndView emergenzaShow(RedirectAttributes redirectAttrs) {
		ModelAndView mv = new ModelAndView();
		int result = satelliteService.lanciatiAttivi().size();
		mv.addObject("emergenza_list_attribute_size", result);
		int resultAll = satelliteService.listAllElements().size();
		if (result<=0||resultAll <= 0) {
			redirectAttrs.addFlashAttribute("warningMessage", "Nessun satellite in stato d emergenza");
			mv.setViewName("redirect:/home");
			return mv;
		}
		mv.addObject("emergenza_list_all_attribute_size", resultAll);
		mv.setViewName("satellite/emergenza");
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return mv;
	}
	
	@PostMapping("/emergenza")
	public String emergenza(RedirectAttributes redirectAttrs) {

		satelliteService.proceduraEmergenza();
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/home";
	}
}
