package za.ntier.process;



import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProcessPara;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;


@org.adempiere.base.annotation.Process
public class CreateASIsForPO extends SvrProcess {
	
	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("ZZ_Approve_Rej_LM")) {
				
			}
				
			else
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para[i]);
		}

	}

	@Override
	protected String doIt() throws Exception {
		
		MOrder mOrder = new MOrder(getCtx(), 1000008, get_TrxName());
		for (MOrderLine mOrderLine : mOrder.getLines()) {
			mOrderLine.setM_AttributeSetInstance_ID(MAttributeSetInstance.generateLot(getCtx(), mOrderLine.getProduct(), get_TrxName()).getM_AttributeSetInstance_ID());
			mOrderLine.saveEx();
		}

		return null;
	}




}
