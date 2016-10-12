package com.rayuniverse.framework.json.validator.impl;

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

import java.util.HashMap;

import com.rayuniverse.framework.json.model.JSONObject;
import com.rayuniverse.framework.json.model.JSONString;
import com.rayuniverse.framework.json.model.JSONValue;
import com.rayuniverse.framework.json.validator.ValidationException;
import com.rayuniverse.framework.json.validator.Validator;
import com.rayuniverse.framework.json.validator.impl.predicates.And;
import com.rayuniverse.framework.json.validator.impl.predicates.Array;
import com.rayuniverse.framework.json.validator.impl.predicates.Bool;
import com.rayuniverse.framework.json.validator.impl.predicates.Complex;
import com.rayuniverse.framework.json.validator.impl.predicates.Content;
import com.rayuniverse.framework.json.validator.impl.predicates.CustomPredicate;
import com.rayuniverse.framework.json.validator.impl.predicates.Decimal;
import com.rayuniverse.framework.json.validator.impl.predicates.Enumeration;
import com.rayuniverse.framework.json.validator.impl.predicates.False;
import com.rayuniverse.framework.json.validator.impl.predicates.Int;
import com.rayuniverse.framework.json.validator.impl.predicates.Length;
import com.rayuniverse.framework.json.validator.impl.predicates.Let;
import com.rayuniverse.framework.json.validator.impl.predicates.Not;
import com.rayuniverse.framework.json.validator.impl.predicates.Nr;
import com.rayuniverse.framework.json.validator.impl.predicates.Null;
import com.rayuniverse.framework.json.validator.impl.predicates.Object;
import com.rayuniverse.framework.json.validator.impl.predicates.Or;
import com.rayuniverse.framework.json.validator.impl.predicates.Properties;
import com.rayuniverse.framework.json.validator.impl.predicates.Range;
import com.rayuniverse.framework.json.validator.impl.predicates.Ref;
import com.rayuniverse.framework.json.validator.impl.predicates.Regexp;
import com.rayuniverse.framework.json.validator.impl.predicates.Simple;
import com.rayuniverse.framework.json.validator.impl.predicates.Str;
import com.rayuniverse.framework.json.validator.impl.predicates.Switch;
import com.rayuniverse.framework.json.validator.impl.predicates.True;



public class ValidatorUtil
{
    public static final String PARAM_NAME = "name";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_RULES = "rules";
    public static final String PARAM_RULE = "rule";
    public static final String PARAM_REF = "*";
    public static final String PARAM_MIN = "min";
    public static final String PARAM_MAX = "max";
    public static final String PARAM_PAIRS = "pairs";
    public static final String PARAM_KEY = "key";
    public static final String PARAM_OPTIONAL = "optional";
    public static final String PARAM_PATTERN="pattern";
    public static final String PARAM_VALUES="values";
    public static final String PARAM_CLASS="class";
    public static final String PARAM_CASE="case";

    public static final String ANONYMOUS_RULE = "[anonymous rule]";



    public static void requiresAttribute(JSONObject aTarget, String aAttrib, Class aValueType)
    throws ValidationException
    {
        if(!aTarget.containsKey(aAttrib))
        {
            final String lMsg = "Attribute not present: \"" + aAttrib + "\"";
            throw new ValidationException(lMsg, aTarget, "MISSING ATTRIBUTE");
        }

        if (!(aValueType.isInstance(aTarget.get(aAttrib))))
        {
            final String lMsg = "Expected other type: \"" + aValueType.getName() + "\"";
            throw new ValidationException(lMsg, aTarget, "UNEXPECTED TYPE");
        }
    }

    public static Validator buildValidator(JSONValue aVal)
    throws ValidationException
    {
        return buildValidator(aVal, new HashMap<String, Validator>());
    }

    public static Validator buildValidator(JSONValue aVal, HashMap<String, Validator> aRuleset)
    throws ValidationException
    {
        if(! aVal.isObject())
        {
            final String lMsg = "A rule should have object type.";
            throw new ValidationException(lMsg, aVal, "OBJECT REQUIRED");
        }

        JSONObject lRule = (JSONObject) aVal;
        //ValidatorUtil.requiresAttribute(lRule, PARAM_NAME, JSONString.class);
        ValidatorUtil.requiresAttribute(lRule, PARAM_TYPE, JSONString.class);

        String lRuleName = ANONYMOUS_RULE;
        if(lRule.containsKey(PARAM_NAME) && lRule.get(PARAM_NAME).isString())
            lRuleName = ((JSONString) lRule.get(PARAM_NAME)).getValue();

        final String lRuleType = ((JSONString) lRule.get(PARAM_TYPE)).getValue();
        Validator lNewValidator = null;

        if("true".equals(lRuleType))        lNewValidator = new True(lRuleName, lRule);
        else if("false".equals(lRuleType))  lNewValidator = new False(lRuleName, lRule);
        else if("or".equals(lRuleType))     lNewValidator = new Or(lRuleName, lRule, aRuleset);
        else if("and".equals(lRuleType))    lNewValidator = new And(lRuleName, lRule, aRuleset);
        else if("not".equals(lRuleType))    lNewValidator = new Not(lRuleName, lRule, aRuleset);
        else if("ref".equals(lRuleType))    lNewValidator = new Ref(lRuleName, lRule, aRuleset);
        else if("complex".equals(lRuleType))lNewValidator = new Complex(lRuleName, lRule);
        else if("array".equals(lRuleType))  lNewValidator = new Array(lRuleName, lRule);
        else if("object".equals(lRuleType)) lNewValidator = new Object(lRuleName, lRule);
        else if("simple".equals(lRuleType)) lNewValidator = new Simple(lRuleName, lRule);
        else if("null".equals(lRuleType))   lNewValidator = new Null(lRuleName, lRule);
        else if("bool".equals(lRuleType))   lNewValidator = new Bool(lRuleName, lRule);
        else if("string".equals(lRuleType)) lNewValidator = new Str(lRuleName, lRule);
        else if("number".equals(lRuleType)) lNewValidator = new Nr(lRuleName, lRule);
        else if("int".equals(lRuleType))    lNewValidator = new Int(lRuleName, lRule);
        else if("decimal".equals(lRuleType))lNewValidator = new Decimal(lRuleName, lRule);
        else if("length".equals(lRuleType)) lNewValidator = new Length(lRuleName, lRule);
        else if("content".equals(lRuleType))lNewValidator = new Content(lRuleName, lRule, aRuleset);
        else if("properties".equals(lRuleType))lNewValidator = new Properties(lRuleName, lRule, aRuleset);
        else if("regexp".equals(lRuleType)) lNewValidator = new Regexp(lRuleName, lRule);
        else if("enum".equals(lRuleType)) lNewValidator = new Enumeration(lRuleName, lRule);
        else if("range".equals(lRuleType)) lNewValidator = new Range(lRuleName, lRule);
        else if("let".equals(lRuleType)) lNewValidator = new Let(lRuleName, lRule, aRuleset);
        else if("custom".equals(lRuleType)) lNewValidator = new CustomPredicate(lRuleName, lRule, aRuleset);
        else if("switch".equals(lRuleType)) lNewValidator = new Switch(lRuleName, lRule, aRuleset);
        else
        {
            final String lMsg = "Unknown validator type: \""  + lRuleType + "\" for rule: \""  + lRuleName + "\"";
            throw new ValidationException(lMsg, lRule, "UNKNOWN VALIDATION TYPE");
        }

        // You cannot refer to anonymous rules. It would leave the door
        // open for twisted rules relying on this feature.
        if(lRuleName != ANONYMOUS_RULE) aRuleset.put(lRuleName, lNewValidator);
        return lNewValidator;
    }
}
