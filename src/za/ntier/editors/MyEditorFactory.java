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
package za.ntier.editors;

import org.adempiere.webui.editor.IEditorConfiguration;
import org.adempiere.webui.editor.WAccountEditor;
import org.adempiere.webui.editor.WAssignmentEditor;
import org.adempiere.webui.editor.WBinaryEditor;
import org.adempiere.webui.editor.WButtonEditor;
import org.adempiere.webui.editor.WChartEditor;
import org.adempiere.webui.editor.WChosenboxListEditor;
import org.adempiere.webui.editor.WChosenboxSearchEditor;
import org.adempiere.webui.editor.WColorEditor;
import org.adempiere.webui.editor.WDashboardContentEditor;
import org.adempiere.webui.editor.WDateEditor;
import org.adempiere.webui.editor.WDatetimeEditor;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WFileDirectoryEditor;
import org.adempiere.webui.editor.WFilenameEditor;
import org.adempiere.webui.editor.WHtmlEditor;
import org.adempiere.webui.editor.WImageEditor;
import org.adempiere.webui.editor.WJsonEditor;
import org.adempiere.webui.editor.WLocatorEditor;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.editor.WPAttributeEditor;
import org.adempiere.webui.editor.WPasswordEditor;
import org.adempiere.webui.editor.WPaymentEditor;
import org.adempiere.webui.editor.WRadioGroupEditor;
import org.adempiere.webui.editor.WRecordIDEditor;
import org.adempiere.webui.editor.WRecordUUIDEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.editor.WTimeEditor;
import org.adempiere.webui.editor.WTimeZoneEditor;
import org.adempiere.webui.editor.WUnknownEditor;
import org.adempiere.webui.editor.WUrlEditor;
import org.adempiere.webui.editor.WYesNoEditor;
import org.adempiere.webui.editor.grid.selection.WGridTabMultiSelectionEditor;
import org.adempiere.webui.editor.grid.selection.WGridTabSingleSelectionEditor;
import org.adempiere.webui.factory.IEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DisplayType;
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
public class MyEditorFactory implements IEditorFactory {
	
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

        /** String (clear/password) */
        if (displayType == DisplayType.String || displayType == DisplayType.PrinterName
            || displayType == DisplayType.Text || displayType == DisplayType.TextLong
            || displayType == DisplayType.Memo)
        {
            if (gridField.isEncryptedField())
            {
                editor = new WPasswordEditor(gridField, tableEditor, editorConfiguration);
            }
            else
            {
            	if (gridField.isHtml()) {
					editor = new WHtmlEditor(gridField, tableEditor, editorConfiguration);
				} else {
					editor = new WStringEditor(gridField, tableEditor, editorConfiguration);
				}
            }
        }
        /** Color */
        else if (displayType == DisplayType.Color) {
        	editor = new WColorEditor(gridField, tableEditor, editorConfiguration);
        }
        
        /** File */
        else if (displayType == DisplayType.FileName)
        {
        	editor = new WFilenameEditor(gridField, tableEditor, editorConfiguration);
        }
        /** File Path */
        else if (displayType == DisplayType.FilePath)
        {
        	editor = new WFileDirectoryEditor(gridField, tableEditor, editorConfiguration);
        }
        /** Number or ID */
        else if (DisplayType.isNumeric(displayType) || displayType == DisplayType.ID)
        {
            editor = new WNumberEditor(gridField, tableEditor, editorConfiguration);
        }

        /** YesNo */
        else if (displayType == DisplayType.YesNo)
        {
            editor = new WYesNoEditor(gridField, tableEditor, editorConfiguration);
            if (tableEditor) {
				((WYesNoEditor)editor).getComponent().setLabel("");
			}
        }

        /** Date */
        else if (DisplayType.isDate(displayType))
        {
        	if (displayType == DisplayType.Time) {
				editor = new WTimeEditor(gridField, tableEditor, editorConfiguration);
			} else if (displayType == DisplayType.DateTime || displayType == DisplayType.TimestampWithTimeZone) {
				editor = new WDatetimeEditor(gridField, tableEditor, editorConfiguration);
			} else {
				editor = new WDateEditor(gridField, tableEditor, editorConfiguration);
			}
        }
        
        /** Chart */
        else if(displayType == DisplayType.Chart)
        {
        	editor = new WChartEditor(gridField, (gridTab == null ? 0 : gridTab.getWindowNo()), tableEditor, editorConfiguration);
        }

        /** Dashboard Content */
        else if(displayType == DisplayType.DashboardContent)
        {
        	editor = new WDashboardContentEditor(gridField, (gridTab == null ? 0 : gridTab.getWindowNo()), tableEditor, editorConfiguration);
        }
        
        /**  Button */
        else if (displayType == DisplayType.Button)
        {
            editor = new WButtonEditor(gridField, tableEditor, editorConfiguration);
        }

        /** Table Direct */
        else if (displayType == DisplayType.TableDir || displayType == DisplayType.TableDirUU ||
                displayType == DisplayType.Table || displayType == DisplayType.TableUU ||
                displayType == DisplayType.List)
        {
            editor = new WTableDirEditor(gridField, tableEditor, editorConfiguration);
        }
        
        else if (displayType == DisplayType.Payment)
        {
        	editor = new WPaymentEditor(gridField, tableEditor, editorConfiguration);
        }

        else if (displayType == DisplayType.URL)
        {
        	editor = new WUrlEditor(gridField, tableEditor, editorConfiguration);
        }

        else if (displayType == DisplayType.Search || displayType == DisplayType.SearchUU)
        {
        	editor = new WSearchEditor(gridField, tableEditor, editorConfiguration);
        }

        else if (displayType == DisplayType.Location)
        {
            editor = new WLocationEditor_Ntier(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.Locator)
        {
        	editor = new WLocatorEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.Account)
        {
        	editor = new WAccountEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.Image)
        {
        	editor = new WImageEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.Binary)
        {
        	editor = new WBinaryEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.PAttribute)
        {
        	editor = new WPAttributeEditor(gridTab, gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.Assignment)
        {
        	editor = new WAssignmentEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.SingleSelectionGrid)
        {
        	editor = new WGridTabSingleSelectionEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.MultipleSelectionGrid)
        {
        	editor = new WGridTabMultiSelectionEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.ChosenMultipleSelectionList || displayType == DisplayType.ChosenMultipleSelectionTable)
        {
        	editor = new WChosenboxListEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.ChosenMultipleSelectionSearch)
        {
        	editor = new WChosenboxSearchEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.RadiogroupList)
        {
        	editor = new WRadioGroupEditor(gridField, tableEditor, editorConfiguration);
        }
        else if (displayType == DisplayType.TimeZoneId)
        {
        	editor = new WTimeZoneEditor(gridField, tableEditor);
        }
		else if (displayType == DisplayType.RecordID)
        {
        	editor = new WRecordIDEditor(gridField, tableEditor, editorConfiguration);
        }
		else if (displayType == DisplayType.RecordUU)
        {
        	editor = new WRecordUUIDEditor(gridField, tableEditor, editorConfiguration);
        }
		else if (displayType == DisplayType.JSON)
        {
        	editor = new WJsonEditor(gridField, tableEditor, editorConfiguration);
        }
        else   if (displayType == RadiogroupListVert)
        {
        	editor = new WRadioGroupEditorVert(gridField, tableEditor, editorConfiguration);
        } else
        {
            editor = new WUnknownEditor(gridField, tableEditor, editorConfiguration);
        }

        return editor;
	}

}
