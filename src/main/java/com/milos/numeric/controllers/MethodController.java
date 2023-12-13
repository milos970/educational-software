package com.milos.numeric.controllers;

import com.milos.numeric.DTO;
import com.milos.numeric.Parameters;
import com.milos.numeric.methods.nonlinear.Bisection;
import com.milos.numeric.methods.nonlinear.RegulaFalsi;
import com.milos.numeric.methods.nonlinear.SimpleIteration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MethodController
{

    @GetMapping("/newton")
    public String newton(Model model)
    {
        model.addAttribute("methodName", "Newtonova metoda");
        model.addAttribute("controller", "/newtonSolve");
        model.addAttribute("parameters", new Parameters());
        return "integration-method";

    }
    @PostMapping("/newtonSolve")
    public String newtonSolve(Model model, Parameters parameters)
    {
        return "redirect:/newtonMethod";
    }


    //*******************************************************************************************************//

    @GetMapping("/simple")
    public String simpleIteration(Model model)
    {
        model.addAttribute("methodName", "Prostá iterácia");
        model.addAttribute("controller", "/simpleSolve");
        model.addAttribute("parameters", new Parameters());
        return "integration-method";

    }
    @PostMapping("/simpleSolve")
    public String simpleIterationSolve(Model model, Parameters parameters)
    {
        SimpleIteration simpleIteration = new SimpleIteration();
        double result = simpleIteration.calculate(parameters);
        model.addAttribute("result", result);
        return "redirect:/simple";
    }


    //*******************************************************************************************************//


    @GetMapping("/regula-falsi")
    public String regulaFalsi(Model model)
    {
        model.addAttribute("methodName", "Regula falsi");
        model.addAttribute("controller", "/regula-falsi");
        model.addAttribute("parameters", new Parameters());
        return "integration-method";

    }
    @PostMapping("/regula-falsi-solve")
    public String regulaFalsiSolve(Model model, Parameters parameters)
    {
        RegulaFalsi regulaFalsi = new RegulaFalsi();
        double result = regulaFalsi.calculate(parameters);
        model.addAttribute("result", result);
        return "redirect:/regula-falsi";
    }


    //*******************************************************************************************************//

    @GetMapping("/bisection")
    public String bisection(Model model)
    {
        model.addAttribute("methodName", "Polovičné delenie intervalov");
        model.addAttribute("controller", "/bisection-solve");
        model.addAttribute("parameters", new Parameters());
        return "integration-method";

    }
    @PostMapping("/bisection-solve")
    public String bisectionSolve(Model model, Parameters parameters)
    {
        Bisection bi = new Bisection();
        double result = bi.calculate(parameters);
        model.addAttribute("result", result);
        return "redirect:/bisection";
    }


    //*******************************************************************************************************//
}
