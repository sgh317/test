package com.rayuniverse.framework.json.serializer.helper.impl;

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


import java.util.*;

import com.rayuniverse.framework.json.model.JSONObject;
import com.rayuniverse.framework.json.model.JSONString;
import com.rayuniverse.framework.json.serializer.marshall.JSONMarshall;
import com.rayuniverse.framework.json.serializer.marshall.MarshallException;


public class IntegerHelper
extends AbstractHelper
{
    public Object parseValue(JSONObject aObjectElement, JSONMarshall aMarshall, HashMap aPool)
    throws MarshallException
    {
        JSONMarshall.requireStringAttribute(aObjectElement, JSONMarshall.RNDR_ATTR_VALUE);
        return new Integer(((JSONString) aObjectElement.get(JSONMarshall.RNDR_ATTR_VALUE)).getValue());
    }

    public Class getHelpedClass()
    {
        return Integer.class;
    }
}