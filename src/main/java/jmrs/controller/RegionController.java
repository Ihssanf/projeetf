package jmrs.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
@CrossOrigin(origins = "http://localhost:3000")
@Controller
@Scope("prototype")
@RequestMapping("/region")
public class RegionController extends BaseController {

}
