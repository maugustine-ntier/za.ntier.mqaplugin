package za.co.ntier.wsp_atr.form;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.component.Borderlayout;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.util.ZKUpdateUtil;
import org.compiere.model.MAttachment;
import org.compiere.model.MAttachmentEntry;
import org.compiere.model.Query;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.North;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Toolbarbutton;

import za.co.ntier.wsp_atr.models.X_ZZ_WSP_ATR_Submitted;

@org.idempiere.ui.zk.annotation.Form(name = "za.co.ntier.wsp_atr.form.WspAtrSubmittedADForm")
public class WspAtrSubmittedADForm extends ADForm implements EventListener<Event> {

    private static final long serialVersionUID = 1L;

    // TODO: set to AD_Process_ID of ValidateAndImportWspAtrDataFromTemplate
    private static final int PROCESS_VALIDATE_IMPORT_ID = 1000000;

    private Borderlayout layout = new Borderlayout();
    private North north = new North();
    private Center center = new Center();

    private Hbox actions = new Hbox();
    private Toolbarbutton btnUpload = new Toolbarbutton("Upload .xlsm");
    private Toolbarbutton btnSubmit = new Toolbarbutton("Submit");
    private Toolbarbutton btnRefresh = new Toolbarbutton("Refresh");
    private Label lblInfo = new Label("");

    private Listbox list = new Listbox();

    @Override
    protected void initForm() {
        ZKUpdateUtil.setWidth(layout, "100%");
        ZKUpdateUtil.setHeight(layout, "100%");
        this.appendChild(layout);

        layout.appendChild(north);
        layout.appendChild(center);

        buildNorth();
        buildList();
        refreshList();
    }

    private void buildNorth() {
        north.setSplittable(false);
        north.setCollapsible(false);
        north.setSize("90px");

        Div northDiv = new Div();
        north.appendChild(northDiv);

        actions.setSpacing("12px");
        actions.appendChild(btnUpload);
        actions.appendChild(btnSubmit);
        actions.appendChild(btnRefresh);

        btnUpload.addEventListener(Events.ON_CLICK, this);
        btnSubmit.addEventListener(Events.ON_CLICK, this);
        btnRefresh.addEventListener(Events.ON_CLICK, this);

        northDiv.appendChild(actions);
        northDiv.appendChild(new Separator());
        northDiv.appendChild(lblInfo);
    }

    private void buildList() {
        center.appendChild(list);
        list.setVflex("1");
        list.setHflex("1");
        list.setMultiple(false);

        Listhead head = new Listhead();
        list.appendChild(head);

        head.appendChild(new Listheader("ID"));
        head.appendChild(new Listheader("Submitted Date"));
        head.appendChild(new Listheader("Uploaded File"));
        head.appendChild(new Listheader("Latest Error File"));
        head.appendChild(new Listheader("Status"));
        head.appendChild(new Listheader("Actions"));
    }

    @Override
    public void onEvent(Event event) throws Exception {
        if (event.getTarget() == btnUpload) {
            doUpload();
            return;
        }
        if (event.getTarget() == btnSubmit) {
            doSubmitSelected();
            return;
        }
        if (event.getTarget() == btnRefresh) {
            refreshList();
            return;
        }

        if ("onDownloadError".equals(event.getName())) {
            int submittedId = (Integer) event.getData();
            downloadLatestError(submittedId);
            return;
        }

        if ("onRunProcess".equals(event.getName())) {
            int submittedId = (Integer) event.getData();
            runValidateImportInBackground(submittedId);
            return;
        }
    }

    // ---------------- UI Actions ----------------

    private void doUpload() {
        Media media = org.zkoss.zul.Fileupload.get();
        if (media == null)
            return;

        String filename = media.getName();
        if (Util.isEmpty(filename, true) || !filename.toLowerCase().endsWith(".xlsm")) {
            throw new AdempiereException("Please upload an .xlsm file");
        }

        byte[] data = getMediaBytes(media);

        // 1) create record
        Properties ctx = Env.getCtx();
        X_ZZ_WSP_ATR_Submitted submitted = new X_ZZ_WSP_ATR_Submitted(ctx, 0, null);
        submitted.setSubmittedDate(new Timestamp(System.currentTimeMillis()));
        submitted.setFileName(filename);
        submitted.setName("WSP/ATR " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        submitted.setZZ_Import_Submitted_Data("N"); // optional status flag
        submitted.saveEx();

        // 2) attach file
        attachToSubmitted(submitted.get_ID(), filename, data);

        lblInfo.setValue("Uploaded: " + filename + " (ID " + submitted.get_ID() + "). Validation/Import started.");
        refreshList();

        // 3) run background
        runValidateImportInBackground(submitted.get_ID());
    }

    private void doSubmitSelected() {
        Integer id = getSelectedId();
        if (id == null)
            throw new AdempiereException("Please select a record first");

        runValidateImportInBackground(id);
        lblInfo.setValue("Validation/Import started for ID " + id);
    }

    private Integer getSelectedId() {
        Listitem sel = list.getSelectedItem();
        if (sel == null)
            return null;
        return (Integer) sel.getValue();
    }

    private void refreshList() {
        list.getItems().clear();

        List<X_ZZ_WSP_ATR_Submitted> records = new Query(
                Env.getCtx(),
                X_ZZ_WSP_ATR_Submitted.Table_Name,
                null,
                null)
                .setOrderBy("ZZ_WSP_ATR_Submitted_ID DESC")
                .list();

        for (X_ZZ_WSP_ATR_Submitted r : records) {
            addRow(r);
        }
    }

    private void addRow(X_ZZ_WSP_ATR_Submitted r) {
        int id = r.get_ID();
        String uploaded = findUploadedFileName(id);
        String latestError = findLatestErrorFileName(id);

        String status;
        if ("Y".equalsIgnoreCase(r.getZZ_Import_Submitted_Data())) status = "Imported";
        else if (!Util.isEmpty(latestError, true)) status = "Has Errors";
        else status = "Pending";

        Listitem item = new Listitem();
        item.setValue(id);

        item.appendChild(new Listcell(String.valueOf(id)));
        item.appendChild(new Listcell(r.getSubmittedDate() != null ? r.getSubmittedDate().toString() : ""));
        item.appendChild(new Listcell(uploaded != null ? uploaded : ""));
        item.appendChild(new Listcell(latestError != null ? latestError : ""));
        item.appendChild(new Listcell(status));

        // actions cell
        Listcell actions = new Listcell();
        Hbox hb = new Hbox();
        hb.setSpacing("8px");

        Toolbarbutton runBtn = new Toolbarbutton("Run");
        runBtn.addEventListener(Events.ON_CLICK, (EventListener<Event>) e ->
                Events.postEvent(new Event("onRunProcess", this, id)));
        hb.appendChild(runBtn);

        if (!Util.isEmpty(latestError, true)) {
            Toolbarbutton dlBtn = new Toolbarbutton("Download Error");
            dlBtn.addEventListener(Events.ON_CLICK, (EventListener<Event>) e ->
                    Events.postEvent(new Event("onDownloadError", this, id)));
            hb.appendChild(dlBtn);
        }

        actions.appendChild(hb);
        item.appendChild(actions);

        list.appendChild(item);
    }

    // ---------------- Background Process ----------------

    private void runValidateImportInBackground(final int submittedId) {
        new Thread(() -> {
            try {
                Properties ctx = Env.getCtx();
                ProcessInfo pi = new ProcessInfo("Validate & Import WSP/ATR", PROCESS_VALIDATE_IMPORT_ID);
                pi.setRecord_ID(submittedId);
                pi.setAD_User_ID(Env.getAD_User_ID(ctx));
                pi.setAD_Client_ID(Env.getAD_Client_ID(ctx));
                //pi.setAD_Org_ID(Env.getAD_Org_ID(ctx));

                // Start via iDempiere process engine
                org.adempiere.util.ProcessUtil.startJavaProcess(ctx, pi, null);

            } catch (Exception ex) {
                // process should attach ERROR file itself
            }
        }, "WSPATR_VALIDATE_IMPORT_" + submittedId).start();
    }

    // ---------------- Download Error ----------------

    private void downloadLatestError(int submittedId) {
        MAttachment att = MAttachment.get(Env.getCtx(), X_ZZ_WSP_ATR_Submitted.Table_ID, submittedId);
        if (att == null || att.getEntryCount() <= 0)
            throw new AdempiereException("No attachment found for record " + submittedId);

        MAttachmentEntry err = findLatestErrorEntry(att);
        if (err == null)
            throw new AdempiereException("No error file found for record " + submittedId);

        try (InputStream is = err.getInputStream()) {
            byte[] data = readAllBytes(is);
            Filedownload.save(
                    data,
                    "application/vnd.ms-excel.sheet.macroEnabled.12",
                    err.getName());
        } catch (Exception e) {
            throw new AdempiereException("Download failed: " + e.getMessage());
        }
    }

    // ---------------- Attachments Helpers ----------------

    private void attachToSubmitted(int submittedId, String filename, byte[] data) {
        MAttachment att = MAttachment.get(Env.getCtx(), X_ZZ_WSP_ATR_Submitted.Table_ID, submittedId);
        if (att == null)
            att = new MAttachment(Env.getCtx(), X_ZZ_WSP_ATR_Submitted.Table_ID, submittedId,null,null);

        att.addEntry(filename, data);
        att.saveEx();
    }

    private String findUploadedFileName(int submittedId) {
        MAttachment att = MAttachment.get(Env.getCtx(), X_ZZ_WSP_ATR_Submitted.Table_ID, submittedId);
        if (att == null || att.getEntryCount() <= 0)
            return null;

        for (MAttachmentEntry e : att.getEntries()) {
            if (e == null) continue;
            String n = e.getName();
            if (Util.isEmpty(n, true)) continue;
            if (n.toUpperCase().startsWith("ERROR")) continue;
            return n;
        }
        return null;
    }

    private String findLatestErrorFileName(int submittedId) {
        MAttachment att = MAttachment.get(Env.getCtx(), X_ZZ_WSP_ATR_Submitted.Table_ID, submittedId);
        if (att == null || att.getEntryCount() <= 0)
            return null;

        MAttachmentEntry e = findLatestErrorEntry(att);
        return e != null ? e.getName() : null;
    }

    private MAttachmentEntry findLatestErrorEntry(MAttachment att) {
        List<MAttachmentEntry> errors = Arrays.stream(att.getEntries())
                .filter(Objects::nonNull)
                .filter(e -> !Util.isEmpty(e.getName(), true))
                .filter(e -> e.getName().toUpperCase().startsWith("ERROR"))
                .collect(Collectors.toList());

        if (errors.isEmpty())
            return null;

        // if your error name includes yyyyMMdd_HHmmss, lexical sort works
        errors.sort(Comparator.comparing(MAttachmentEntry::getName));
        return errors.get(errors.size() - 1);
    }

    // ---------------- Media / IO Helpers ----------------

    private byte[] getMediaBytes(Media media) {
        try {
            // xlsm should be binary
            if (media.isBinary()) {
                return media.getByteData();
            }
            // fallback (should not happen)
            return media.getStringData().getBytes("UTF-8");
        } catch (Exception e) {
            throw new AdempiereException("Failed to read uploaded file: " + e.getMessage());
        }
    }

    private byte[] readAllBytes(InputStream is) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int r;
        while ((r = is.read(buf)) > 0) {
            bos.write(buf, 0, r);
        }
        return bos.toByteArray();
    }
}
