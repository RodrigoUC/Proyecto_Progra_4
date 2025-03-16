package org.example.seguroform.presentation.slots;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.seguroform.logic.Service;
import org.example.seguroform.logic.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller("slots")
@RequestMapping("/presentation/slots")
public class controller {
    @Autowired
    private Service service;

    @GetMapping("/create")
    public String createSlot(@ModelAttribute Slot slot) {
        service.slotAdd(slot);
        return "redirect:/presentation/login/ViewLogin"; // Hay que cambiar las rutas
    }
    @GetMapping("/delete/{id}")
    public String deleteSlot(@PathVariable("id") Integer id) {
        service.slotDel(id);
        return "redirect:/presentation/login/ViewLogin";
    }
}