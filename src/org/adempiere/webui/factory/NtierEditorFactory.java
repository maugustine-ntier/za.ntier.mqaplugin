/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2010 Heng Sin Low                							  *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.factory;

import org.adempiere.webui.editor.IEditorConfiguration;
import org.adempiere.webui.editor.WEditor;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.osgi.service.component.annotations.Component;

import za.ntier.webui.editor.WRadioGroupEditorVert;

/**
 * Default implementation of {@link IEditorFactory}
 * @author hengsin
 *
 */
@Component(

		 property= {"service.ranking:Integer=2"},
		 service = org.adempiere.webui.factory.IEditorFactory.class
		 )
public class NtierEditorFactory implements IEditorFactory {
	public static final int RadiogroupListVert = 1000003;

	@Override
	public WEditor getEditor(GridTab gridTab, GridField gridField,
			boolean tableEditor) {
		return getEditor(gridTab, gridField, tableEditor, null);
	}
	
	@Override
	public WEditor getEditor(GridTab gridTab, GridField gridField,
			boolean tableEditor, IEditorConfiguration editorConfiguration) {
		if (gridField == null)
        {
            return null;
        }

        WEditor editor = null;
        int displayType = gridField.getDisplayType();

        /** Not a Field */
        if (gridField.isHeading())
        {
            return null;
        }

       
        if (displayType == RadiogroupListVert)
        {
        	editor = new WRadioGroupEditorVert(gridField, tableEditor, editorConfiguration);
        }
       
        return editor;
	}

}
