/******************************************************************************
 * Copyright (C) 2014 Thomas Bayen                                            *
 * Copyright (C) 2014 Jakob Bayen KG & BX Service GmbH             			  *
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
package za.ntier.report.jasper;

import org.adempiere.base.AnnotationBasedProcessFactory;
import org.adempiere.base.IProcessFactory;
import org.adempiere.util.ProcessUtil;
import org.compiere.process.ProcessCall;
import org.osgi.service.component.annotations.Component;

/**
 * ProcessFactory to start the ReportStarter class.
 * 
 * Before this Factory was initiated the class was started with the
 * DefaultProcessFactory because its package namespace was exported and joined
 * into the org.compiere.report package of the org.adempiere.base plugin via
 * Split Packages (through the Require-Bundle technique) See
 * http://wiki.osgi.org/wiki/Split_Packages why this is not the best idea.
 * Especially this prevents us from exchange the JasperReports plugin with
 * another implementation.
 * 
 * @author tbayen
 */
@Component(immediate = true, service = IProcessFactory.class, property = {"service.ranking:Integer=100"})
public class ProcessFactory extends AnnotationBasedProcessFactory {

	public static final String JASPER_STARTER_CLASS_DEPRECATED = "org.compiere.report.ReportStarter";

	@Override
	public ProcessCall newProcessInstance(String className) {
		if (ProcessUtil.JASPER_STARTER_CLASS.equals(className))
			return new DazzleReportStarter();
		// this is for compatibility with older installations
		if (JASPER_STARTER_CLASS_DEPRECATED.equals(className))
			return new DazzleReportStarter();
		else
			return null;
	}

	@Override
	protected String[] getPackages() {
		return new String[] {"za.ntier.report.jasper"};
	}
}
