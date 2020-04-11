package net.hka.onlineshopping.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import net.hka.shoppingbackend.dto.Product;

/**
 * 
 * Validating on the products' image file before doing changes on the database
 * side. This is to overcome the lack of DTO validations on this kind of fields,
 * because this kind of validations cannot be accomplished using the regular
 * annotations in DTO class files
 * 
 */
@Component
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;

		// check whether any file has been selected
		if (product.getFile() == null || product.getFile().getOriginalFilename().equals("")) {
			errors.rejectValue("file", null, "Please select a file to upload!");
			return;
		}

		// check if the file type is one of certain type
		if (!(product.getFile().getContentType().equals("image/jpeg")
				|| product.getFile().getContentType().equals("image/png"))
				|| product.getFile().getContentType().equals("image/gif")) {
			errors.rejectValue("file", null, "Please select an image file to upload!");
			return;
		}

	}

}
