package com.rayuniverse.framework.json.validator.impl.predicates;

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


import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;

import com.rayuniverse.framework.json.model.JSONArray;
import com.rayuniverse.framework.json.model.JSONObject;
import com.rayuniverse.framework.json.model.JSONValue;
import com.rayuniverse.framework.json.validator.ValidationException;
import com.rayuniverse.framework.json.validator.Validator;
import com.rayuniverse.framework.json.validator.impl.ValidatorUtil;


public class Or
extends Predicate
{
    private List<Validator> rules = new LinkedList<Validator>();

    public Or(String aName, JSONObject aRule, HashMap<String,Validator> aRuleset)
    throws ValidationException
    {
        super(aName, aRule);
        ValidatorUtil.requiresAttribute(aRule, ValidatorUtil.PARAM_RULES, JSONArray.class);

        List<JSONValue> lRules = ((JSONArray) aRule.get(ValidatorUtil.PARAM_RULES)).getValue();
        for (JSONValue lRule : lRules)
        {
            Validator lValidator = ValidatorUtil.buildValidator(lRule, aRuleset);
            rules.add(lValidator);
        }
    }

    public void validate(JSONValue aValue)
    throws ValidationException
    {
        for (Validator rule1 : rules)
        {
            try
            {
                rule1.validate(aValue);
                // If we get here, the current validator succeeded.
                // We only need a single success!
                return;
            }
            catch (ValidationException e)
            {
                // This rule failed. Ignore for the time being.
            }
        }
        // If we get here, then all rules failed.
        // If all rules fail, we fail as well.
        fail("All or rules failed.", aValue);
    }
}
