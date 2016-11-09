package com.rair.jsf.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.rair.dao.AirportRepository;
import com.rair.domain.Airport;
import com.rair.jsfbeans.AirportServiceBean;

@FacesConverter("airportConverter")
public class AirportConverter implements Converter {
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		System.out.println("De converteerwaarde is: " + value);
		if (value != null && value.trim().length() > 0) {
			try {
				AirportServiceBean airportServiceBean = (AirportServiceBean)fc.getExternalContext().getApplicationMap().get("airportServiceBean");
				System.out.println(airportServiceBean);
				Airport airport = airportServiceBean.findAirportByName(value);
				System.out.println(airport);
				return airportServiceBean.findAirportByName(value);
			} catch (NumberFormatException e) {
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion error", "Not a valid airport"));
			}
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object object) {
		if(object != null) {
            return String.valueOf(((Airport) object).getName());
        }
        else {
            return null;
        }
	}

}
