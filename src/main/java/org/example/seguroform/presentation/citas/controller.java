package org.example.seguroform.presentation.citas;

import org.example.seguroform.logic.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@org.springframework.stereotype.Controller("citas")
@RequestMapping("/presentation/citas")
public class controller {
    @Autowired
    private Service service;
}
