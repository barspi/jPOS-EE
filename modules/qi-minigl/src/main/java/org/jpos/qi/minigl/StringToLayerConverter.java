/*
 * jPOS Project [http://jpos.org]
 * Copyright (C) 2000-2025 jPOS Software SRL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jpos.qi.minigl;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import org.jpos.gl.Journal;
import org.jpos.gl.Layer;

public class StringToLayerConverter implements Converter<Layer, String> {
    private Journal journal;

    public StringToLayerConverter(Journal journal) {
        super();
        this.journal = journal;
    }
    @Override
    public Result<String> convertToModel (Layer value, ValueContext context) {
        if (value != null)
            return Result.ok(String.valueOf(value.getId()));
        return Result.ok(null);
    }

    @Override
    public Layer convertToPresentation (String value, ValueContext context) {
        if (value != null) {
            TransactionsHelper helper = new TransactionsHelper();
            return helper.getLayer(Short.parseShort(value), journal);
        }
        return null;
    }
}
