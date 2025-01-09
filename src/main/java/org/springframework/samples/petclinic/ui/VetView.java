package org.springframework.samples.petclinic.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;

@Route("")
@RouteAlias("vets")
public class VetView extends VerticalLayout {
	public VetView(VetRepository vetRepository) {
		var grid = new Grid<>(Vet.class);
		grid.setItems(vetRepository.findAll());
		grid.setColumns("firstName", "lastName", "specialties");
		grid.setSizeFull();
		add(grid);

		setSizeFull();
	}
}
