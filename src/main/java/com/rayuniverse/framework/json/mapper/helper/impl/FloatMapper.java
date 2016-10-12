package com.rayuniverse.framework.json.mapper.helper.impl;

/*
    JSONTools - Java JSON Tools
    Copyright (C) 2006-2008 S.D.I.-Consulting BVBA
    http://www.sdi-consulting.com
    mailto://nospam@sdi-consulting.com

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
*/


import java.math.BigDecimal;

import com.rayuniverse.framework.json.mapper.MapperException;
import com.rayuniverse.framework.json.mapper.helper.SimpleMapperHelper;
import com.rayuniverse.framework.json.model.JSONDecimal;
import com.rayuniverse.framework.json.model.JSONInteger;
import com.rayuniverse.framework.json.model.JSONString;
import com.rayuniverse.framework.json.model.JSONValue;


public class FloatMapper
implements SimpleMapperHelper
{
    public Class getHelpedClass()
    {
        return Float.class;
    }

    public Object toJava(JSONValue aValue, Class aRequestedClass) throws MapperException
    {
        if(aValue.isString())
        {
            try
            {
                return Float.parseFloat(((JSONString)aValue).getValue());
            }
            catch (NumberFormatException e)
            {
                throw new MapperException("FloatMapper cannot map value: " + ((JSONString)aValue).getValue());
            }
        }
        else if(aValue.isDecimal()) return ((JSONDecimal) aValue).getValue().floatValue();
        else if(aValue.isInteger()) return ((JSONInteger)aValue).getValue().floatValue();        
        else throw new MapperException("FloatMapper cannot map: " + aValue.getClass().getName());        
    }

    public JSONValue toJSON(Object aPojo)
    throws MapperException
    {
        if(!Float.class.isAssignableFrom(aPojo.getClass())) throw new MapperException("FloatMapper cannot map: " + aPojo.getClass().getName());
        return new JSONDecimal(new BigDecimal(aPojo.toString()));
    }
}