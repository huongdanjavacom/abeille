/*
 * Copyright (C) 2005 Jeff Tassin
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.jeta.swingbuilder.codegen.builder.properties;

import java.lang.reflect.Method;

import com.jeta.forms.gui.beans.JETAPropertyDescriptor;
import com.jeta.forms.store.properties.BooleanProperty;
import com.jeta.swingbuilder.codegen.builder.BaseBeanWriter;
import com.jeta.swingbuilder.codegen.builder.DeclarationManager;
import com.jeta.swingbuilder.codegen.builder.MethodStatement;
import com.jeta.swingbuilder.codegen.builder.PropertyWriter;

public class BooleanPropertyWriter implements PropertyWriter {

	/**
	 * PropertyWriter implementation
	 */
	public void writeProperty(DeclarationManager declMgr, BaseBeanWriter writer, JETAPropertyDescriptor pd, Object value) {
		try {
			Boolean bval = false;
			if (value instanceof Boolean) {
				bval = (Boolean) value;
			}else if (value instanceof BooleanProperty) {
				bval = ((BooleanProperty)value).getValue();
			}else{
				System.out.println("error "+this.getClass().getSimpleName()+"::"+value.getClass().getName());
				return;
			}
			Method write = pd.getWriteMethod();
			if (write != null) {
				MethodStatement ms = new MethodStatement(writer.getBeanVariable(), write.getName());
				if (bval.booleanValue())
					ms.addParameter("true");
				else
					ms.addParameter("false");

				writer.addStatement(ms);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
