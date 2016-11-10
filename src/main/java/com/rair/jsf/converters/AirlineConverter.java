package com.rair.jsf.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.rair.domain.Airline;
import com.rair.domain.Airport;
import com.rair.jsfbeans.AirlineService;

@FacesConverter("airlineConverter")
public class AirlineConverter implements Converter{
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		System.out.println("De converteerwaarde is: " + value);
		if (value != null && value.trim().length() > 0) {
			try {
				AirlineService airlineService = (AirlineService)fc.getExternalContext().getApplicationMap().get("airlineService");
				System.out.println(airlineService);
				Airline airline = airlineService.findAirlineByName(value);
				System.out.println(airline);
				return airline;
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion error", "Not a valid airline"));
			}
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if(object != null) {
            return String.valueOf(((Airline) object).getName());
        }
        else {
            return null;
        }
	}

}
