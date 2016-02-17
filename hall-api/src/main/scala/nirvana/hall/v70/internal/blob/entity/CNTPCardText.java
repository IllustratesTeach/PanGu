package nirvana.hall.v70.internal.blob.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class CNTPCardText implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private Short dbId;

	private String updateUser;

	private Timestamp updateTime;

	private String misPersonId;

	private String name;

	private String alias;

	private String namePinYin;

	private String birthDate;

	private String sexCode;

	private String nation;

	private String nationality;

	private String shenfenId;

	private String certificateType;

	private String certificateCode;

	private String hukouPlaceCode;

	private String hukouPlace;

	private String addressCode;

	private String address;

	private String caseClass1Code;

	private String caseClass2Code;

	private String caseClass3Code;

	private Byte isCriminalRecord;

	private String personClassCode;

	private String printDate;

	private String printerName;

	private String printerUnitCode;

	private String printerUnitName;

	private String comments;

	private String criminalRecord;

	private Long rcn;

	private String casenum;

	private Boolean isDnaCaptured;

	private String dna1;

	private String dna2;

	private String formerName1;

	private String formerName2;

	private String familyName;

	private String givenName;

	private String middleName;

	private String height;

	private String footLength;

	private String bloodType;

	private String weight;

	private String accent;

	private String hairColor;

	private String eyeColor;

	private String educationLevel;

	private String employerName;

	private String employerAddress;

	private String employerUnitCode;

	private String hukouPcsName;

	private String hukouPcsUnitCode;

	private String residencePcsName;

	private String residencePcsUnitCode;

	private String maritalStatus;

	private String email1;

	private String email2;

	private String contactPhone1;

	private String contactPhone2;

	private String deathDate;

	private String bodyFeature;

	private String bodyType;

	private String headType;

	private String jobTitle;

	private String qq;

	private String occupation;

	private String birthPlace;

	private String birthPlaceUnitCode;

	private String politicalAffiliation;

	private String contactPhone1Type;

	private String contactPhone2Type;

	private String enrollTypeCode;

	private String enrollReasonCode;

	private String enrollSite;

	private Byte isDeaf;

	private Byte isFingerMissing;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMisPersonId() {
		return this.misPersonId;
	}

	public void setMisPersonId(String misPersonId) {
		this.misPersonId = misPersonId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getNamePinYin() {
		return this.namePinYin;
	}

	public void setNamePinYin(String namePinYin) {
		this.namePinYin = namePinYin;
	}

	public String getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getSexCode() {
		return this.sexCode;
	}

	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getShenfenId() {
		return this.shenfenId;
	}

	public void setShenfenId(String shenfenId) {
		this.shenfenId = shenfenId;
	}

	public String getCertificateType() {
		return this.certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getCertificateCode() {
		return this.certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	public String getHukouPlaceCode() {
		return this.hukouPlaceCode;
	}

	public void setHukouPlaceCode(String hukouPlaceCode) {
		this.hukouPlaceCode = hukouPlaceCode;
	}

	public String getHukouPlace() {
		return this.hukouPlace;
	}

	public void setHukouPlace(String hukouPlace) {
		this.hukouPlace = hukouPlace;
	}

	public String getAddressCode() {
		return this.addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCaseClass1Code() {
		return this.caseClass1Code;
	}

	public void setCaseClass1Code(String caseClass1Code) {
		this.caseClass1Code = caseClass1Code;
	}

	public String getCaseClass2Code() {
		return this.caseClass2Code;
	}

	public void setCaseClass2Code(String caseClass2Code) {
		this.caseClass2Code = caseClass2Code;
	}

	public String getCaseClass3Code() {
		return this.caseClass3Code;
	}

	public void setCaseClass3Code(String caseClass3Code) {
		this.caseClass3Code = caseClass3Code;
	}

	public Byte getIsCriminalRecord() {
		return this.isCriminalRecord;
	}

	public void setIsCriminalRecord(Byte isCriminalRecord) {
		this.isCriminalRecord = isCriminalRecord;
	}

	public String getPersonClassCode() {
		return this.personClassCode;
	}

	public void setPersonClassCode(String personClassCode) {
		this.personClassCode = personClassCode;
	}

	public String getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getPrinterName() {
		return this.printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getPrinterUnitCode() {
		return this.printerUnitCode;
	}

	public void setPrinterUnitCode(String printerUnitCode) {
		this.printerUnitCode = printerUnitCode;
	}

	public String getPrinterUnitName() {
		return this.printerUnitName;
	}

	public void setPrinterUnitName(String printerUnitName) {
		this.printerUnitName = printerUnitName;
	}

	public String getCriminalRecord() {
		return this.criminalRecord;
	}

	public void setCriminalRecord(String criminalRecord) {
		this.criminalRecord = criminalRecord;
	}

	public Long getRcn() {
		return this.rcn;
	}

	public void setRcn(Long rcn) {
		this.rcn = rcn;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCasenum() {
		return this.casenum;
	}

	public void setCasenum(String casenum) {
		this.casenum = casenum;
	}

	public Short getDbId() {
		return this.dbId;
	}

	public void setDbId(Short dbId) {
		this.dbId = dbId;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateInfo(OpInfo opInfo) {
		if (opInfo == null)
			return;
	}

	public void setUpdateInfo(OpInfo opInfo) {
		if (opInfo == null)
			return;
		this.updateUser = opInfo.curUser;
		this.updateTime = opInfo.curTime;
	}

	public Boolean getIsDnaCaptured() {
		return this.isDnaCaptured;
	}

	public void setIsDnaCaptured(Boolean isDnaCaptured) {
		this.isDnaCaptured = isDnaCaptured;
	}

	public String getDna1() {
		return this.dna1;
	}

	public void setDna1(String dna1) {
		this.dna1 = dna1;
	}

	public String getDna2() {
		return this.dna2;
	}

	public void setDna2(String dna2) {
		this.dna2 = dna2;
	}

	public String getFormerName1() {
		return this.formerName1;
	}

	public void setFormerName1(String formerName1) {
		this.formerName1 = formerName1;
	}

	public String getFormerName2() {
		return this.formerName2;
	}

	public void setFormerName2(String formerName2) {
		this.formerName2 = formerName2;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return this.givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getFootLength() {
		return this.footLength;
	}

	public void setFootLength(String footLength) {
		this.footLength = footLength;
	}

	public String getBloodType() {
		return this.bloodType;
	}

	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getAccent() {
		return this.accent;
	}

	public void setAccent(String accent) {
		this.accent = accent;
	}

	public String getHairColor() {
		return this.hairColor;
	}

	public void setHairColor(String hairColor) {
		this.hairColor = hairColor;
	}

	public String getEyeColor() {
		return this.eyeColor;
	}

	public void setEyeColor(String eyeColor) {
		this.eyeColor = eyeColor;
	}

	public String getEducationLevel() {
		return this.educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getEmployerName() {
		return this.employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getEmployerAddress() {
		return this.employerAddress;
	}

	public void setEmployerAddress(String employerAddress) {
		this.employerAddress = employerAddress;
	}

	public String getEmployerUnitCode() {
		return this.employerUnitCode;
	}

	public void setEmployerUnitCode(String employerUnitCode) {
		this.employerUnitCode = employerUnitCode;
	}

	public String getHukouPcsName() {
		return this.hukouPcsName;
	}

	public void setHukouPcsName(String hukouPcsName) {
		this.hukouPcsName = hukouPcsName;
	}

	public String getHukouPcsUnitCode() {
		return this.hukouPcsUnitCode;
	}

	public void setHukouPcsUnitCode(String hukouPcsUnitCode) {
		this.hukouPcsUnitCode = hukouPcsUnitCode;
	}

	public String getResidencePcsName() {
		return this.residencePcsName;
	}

	public void setResidencePcsName(String residencePcsName) {
		this.residencePcsName = residencePcsName;
	}

	public String getResidencePcsUnitCode() {
		return this.residencePcsUnitCode;
	}

	public void setResidencePcsUnitCode(String residencePcsUnitCode) {
		this.residencePcsUnitCode = residencePcsUnitCode;
	}

	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getEmail1() {
		return this.email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return this.email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getContactPhone1() {
		return this.contactPhone1;
	}

	public void setContactPhone1(String contactPhone1) {
		this.contactPhone1 = contactPhone1;
	}

	public String getContactPhone2() {
		return this.contactPhone2;
	}

	public void setContactPhone2(String contactPhone2) {
		this.contactPhone2 = contactPhone2;
	}

	public String getDeathDate() {
		return this.deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public String getBodyFeature() {
		return this.bodyFeature;
	}

	public void setBodyFeature(String bodyFeature) {
		this.bodyFeature = bodyFeature;
	}

	public String getHeadType() {
		return this.headType;
	}

	public void setHeadType(String headType) {
		this.headType = headType;
	}

	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getBirthPlace() {
		return this.birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getBirthPlaceUnitCode() {
		return this.birthPlaceUnitCode;
	}

	public void setBirthPlaceUnitCode(String birthPlaceUnitCode) {
		this.birthPlaceUnitCode = birthPlaceUnitCode;
	}

	public String getBodyType() {
		return this.bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getContactPhone1Type() {
		return this.contactPhone1Type;
	}

	public void setContactPhone1Type(String contactPhone1Type) {
		this.contactPhone1Type = contactPhone1Type;
	}

	public String getContactPhone2Type() {
		return this.contactPhone2Type;
	}

	public void setContactPhone2Type(String contactPhone2Type) {
		this.contactPhone2Type = contactPhone2Type;
	}

	public String getEnrollTypeCode() {
		return this.enrollTypeCode;
	}

	public void setEnrollTypeCode(String enrollTypeCode) {
		this.enrollTypeCode = enrollTypeCode;
	}

	public String getEnrollReasonCode() {
		return this.enrollReasonCode;
	}

	public void setEnrollReasonCode(String enrollReasonCode) {
		this.enrollReasonCode = enrollReasonCode;
	}

	public String getEnrollSite() {
		return this.enrollSite;
	}

	public void setEnrollSite(String enrollSite) {
		this.enrollSite = enrollSite;
	}

	public Byte getIsDeaf() {
		return this.isDeaf;
	}

	public void setIsDeaf(Byte isDeaf) {
		this.isDeaf = isDeaf;
	}

	public Byte getIsFingerMissing() {
		return this.isFingerMissing;
	}

	public void setIsFingerMissing(Byte isFingerMissing) {
		this.isFingerMissing = isFingerMissing;
	}

	public String getPoliticalAffiliation() {
		return this.politicalAffiliation;
	}

	public void setPoliticalAffiliation(String politicalAffiliation) {
		this.politicalAffiliation = politicalAffiliation;
	}
}