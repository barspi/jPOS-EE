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

import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.shared.ui.ErrorLevel;
import org.jpos.ee.DB;
import org.jpos.gl.Account;
import org.jpos.qi.QI;

public class AccountValidator implements Validator<Account> {
    private QI app;
    private Class clazz;

    public AccountValidator (QI app, Class clazz) {
        super();
        this.app = app;
        this.clazz = clazz;
    }

    @Override
    public ValidationResult apply (Account value, ValueContext context) {
        Account acct = null;
        if (!context.getHasValue().isPresent())
            return ValidationResult.ok();
        boolean isReadOnly = context.getHasValue().get().isReadOnly();
        if (isReadOnly)
            return ValidationResult.ok();
        if (value != null) {
            try {
                acct = DB.exec(db -> db.session().get(Account.class, value.getId()));
            } catch (Exception e) {
                app.getLog().createError(e.getMessage());
            }
        }
        if (acct == null)
            return ValidationResult.create(app.getMessage("errorMessage.willCreateAccountAndReceivables"),
                    ErrorLevel.INFO);
        else if (clazz.isInstance(value))
            return ValidationResult.create(app.getMessage("errorMessage.accountExists"), ErrorLevel.INFO);
        return ValidationResult.ok();

    }
}
