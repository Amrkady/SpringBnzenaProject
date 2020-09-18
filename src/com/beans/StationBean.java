package com.beans;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.entities.Attachment;
import com.entities.Stations;
import com.models.AttachmentModel;
import com.services.DepartmentService;

import common.util.FtpTransferFile;
import common.util.MsgEntry;
import common.util.Utils;

@ManagedBean(name = "stationBean")
@ViewScoped
public class StationBean {
	@ManagedProperty(value = "#{departmentServiceImpl}")
	private DepartmentService departmentServiceImpl;
	private Stations st = new Stations();
	private Integer stId;
	private List<AttachmentModel> attachs = new ArrayList<AttachmentModel>();
	List<Attachment> attachsList = new ArrayList<Attachment>();
	@PostConstruct
	public void init() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		stId = (Integer) session.getAttribute("stationId");
		if (stId != null) {
			st = (Stations) departmentServiceImpl.findEntityById(Stations.class, stId);
			attachsList = departmentServiceImpl.loadAttachments(stId);
		}
	}

	public StreamedContent getFile(Attachment attach) {
		InputStream stream = null;
		StreamedContent file = null;
		try {
			stream = FtpTransferFile.getFileStream(attach.getAttName());
			file = new DefaultStreamedContent(stream, "application/pdf", attach.getRealName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public String updateStation() {
		try {
			if (attachs != null && attachs.size() > 0) {
				List<Attachment> atts = Utils.SaveAttachementsToFtpServer(attachs);
				for (Attachment attachment : atts) {
					attachment.setStationId(stId);
					departmentServiceImpl.save(attachment);
				}
			}
			departmentServiceImpl.updateStation(st);
			MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.update"));
			attachsList = departmentServiceImpl.loadAttachments(stId);
			attachs = new ArrayList<AttachmentModel>();
		} catch (Exception e) {
			MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.update"));
			e.printStackTrace();
		}
		return "";
	}

	public void NewRecordupload(FileUploadEvent event) {

		try {
			AttachmentModel attach = new AttachmentModel();
			attach.setAttachRealName(event.getFile().getFileName());
			attach.setAttachSize(event.getFile().getSize());
			attach.setAttachStream(event.getFile().getInputstream());
			attach.setAttachExt(FilenameUtils.getExtension(event.getFile().getFileName()));
			attach.setRealName(Utils.generateRandomName() + "." + attach.getAttachExt());
			attachs.add(attach);

		} catch (Exception e) {

		}
	}

	public String deleteAttachment(Attachment attachment) {
		if (attachment != null) {
			try {
				departmentServiceImpl.delete(attachment);
				MsgEntry.addInfoMessage(Utils.loadMessagesFromFile("success.delete"));
				attachsList = departmentServiceImpl.loadAttachments(stId);
			} catch (Exception e) {
				MsgEntry.addErrorMessage(Utils.loadMessagesFromFile("error.delete"));
				e.printStackTrace();
			}
		}
		return "";
	}

	public DepartmentService getDepartmentServiceImpl() {
		return departmentServiceImpl;
	}

	public void setDepartmentServiceImpl(DepartmentService departmentServiceImpl) {
		this.departmentServiceImpl = departmentServiceImpl;
	}

	public Stations getSt() {
		return st;
	}

	public void setSt(Stations st) {
		this.st = st;
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

	public List<AttachmentModel> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<AttachmentModel> attachs) {
		this.attachs = attachs;
	}

	public List<Attachment> getAttachsList() {
		return attachsList;
	}

	public void setAttachsList(List<Attachment> attachsList) {
		this.attachsList = attachsList;
	}

}
