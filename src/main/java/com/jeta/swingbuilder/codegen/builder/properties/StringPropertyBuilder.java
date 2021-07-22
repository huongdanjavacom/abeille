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
import com.jeta.forms.store.properties.StringListProperty;
import com.jeta.forms.store.properties.StringProperty;
import com.jeta.swingbuilder.codegen.builder.BaseBeanWriter;
import com.jeta.swingbuilder.codegen.builder.DeclarationManager;
import com.jeta.swingbuilder.codegen.builder.MethodStatement;
import com.jeta.swingbuilder.codegen.builder.PropertyWriter;
import com.jeta.swingbuilder.codegen.builder.StringExpression;

public class StringPropertyBuilder implements PropertyWriter {

	/**
	 * PropertyWriter implementation
	 */
	public void writeProperty(DeclarationManager declMgr, BaseBeanWriter writer, JETAPropertyDescriptor pd, Object value) {
		try {
			if (value instanceof String) {
				Method write = pd.getWriteMethod();
				if (write != null) {
					MethodStatement ms = new MethodStatement(writer.getBeanVariable(), write.getName());
					ms.addParameter(StringExpression.quoteString((String) value));
					writer.addStatement(ms);
				}
			}else if(value instanceof StringProperty){
				Method write = pd.getWriteMethod();
				if (write != null) {
					MethodStatement ms = new MethodStatement(writer.getBeanVariable(), write.getName());
					ms.addParameter(StringExpression.quoteString(((StringProperty) value).getValue()));
					writer.addStatement(ms);
				}
			}else if(value instanceof StringListProperty){
				Method write = pd.getWriteMethod();
				if (write != null) {
					MethodStatement ms = new MethodStatement(writer.getBeanVariable(), write.getName());
					ms.addParameter(StringExpression.quoteString(((StringListProperty) value).getValue()));
					writer.addStatement(ms);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
