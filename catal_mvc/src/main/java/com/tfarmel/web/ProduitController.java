package com.tfarmel.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tfarmel.dao.ProduitRepository;
import com.tfarmel.entities.Produit;

@Controller
public class ProduitController {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(Model model, @RequestParam(name="page", defaultValue="0") int page,
									 @RequestParam(name="motCle", defaultValue="") String mc) {
		Page<Produit> pageProduits = produitRepository.findByDesignationContains(mc, PageRequest.of(page, 5));
		model.addAttribute("listProduits", pageProduits.getContent());
		model.addAttribute("pages", new int[pageProduits.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "produits";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(Long id, int page, String motCle) {
		produitRepository.deleteById(id);
		return "redirect:/index?page="+page+"&motCle="+motCle;
	}

}
