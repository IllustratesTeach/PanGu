-- Loading GAFIS_GATHER_NODE...
insert into GAFIS_GATHER_NODE (pk_id, node_code, node_name, node_request, delete_flag, create_user_id, create_datetime, update_user_id, update_datetime, node_img)
values ('8a81876b3dfd20a3013dfd21d7680001', '001', '人员基本信息', 'personObjedit.do', 1, '1', PARSEDATETIME('12-04-2013 15:24:46', 'dd-MM-yyyy hh:mm:ss'), '1', PARSEDATETIME('09-09-2013 15:24:23', 'dd-MM-yyyy hh:mm:ss'), 'card');
insert into GAFIS_GATHER_NODE (pk_id, node_code, node_name, node_request, delete_flag, create_user_id, create_datetime, update_user_id, update_datetime, node_img)
values ('8a8187ee3e15f790013e15f8df760001', '002', '人像采集', 'gatherFaceEdit.jsp', 1, '1', PARSEDATETIME('17-04-2013 11:10:31', 'dd-MM-yyyy hh:mm:ss'), '1', PARSEDATETIME('22-10-2014 17:51:05', 'dd-MM-yyyy hh:mm:ss'), 'cream');
insert into GAFIS_GATHER_NODE (pk_id, node_code, node_name, node_request, delete_flag, create_user_id, create_datetime, update_user_id, update_datetime, node_img)
values ('8a8187ee3e209c87013e20a577250004', '003', '指掌纹采集', 'gatherFingerPalmEdit.jsp', 1, '1', PARSEDATETIME('19-04-2013 12:55:14', 'dd-MM-yyyy hh:mm:ss'), '1', PARSEDATETIME('22-04-2013 10:47:55', 'dd-MM-yyyy hh:mm:ss'), 'font');
-- Loading GAFIS_GATHER_FIELDSET...
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000001', '8a81876b3dfd20a3013dfd21d7680001', '姓名', 'name', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000002', '8a81876b3dfd20a3013dfd21d7680001', '身份证号码', 'idCardNo', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000003', '8a81876b3dfd20a3013dfd21d7680001', '别名', 'aliasName', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000004', '8a81876b3dfd20a3013dfd21d7680001', '曾用名', 'usedName', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000005', '8a81876b3dfd20a3013dfd21d7680001', '性别', 'sexCode', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000006', '8a81876b3dfd20a3013dfd21d7680001', '国籍', 'nativeplaceCode', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000007', '8a81876b3dfd20a3013dfd21d7680001', '民族', 'nationCode', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000008', '8a81876b3dfd20a3013dfd21d7680001', '出生日期', 'birthdayST', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000009', '8a81876b3dfd20a3013dfd21d7680001', '政治面貌', 'politicsCode', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000010', '8a81876b3dfd20a3013dfd21d7680001', '文化程度', 'cultureCode', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000011', '8a81876b3dfd20a3013dfd21d7680001', '婚姻状况', 'ifMarryCode', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000012', '8a81876b3dfd20a3013dfd21d7680001', '采集类别', 'gatherCategory', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000013', '8a81876b3dfd20a3013dfd21d7680001', '人员类型', 'personCategory', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000014', '8a81876b3dfd20a3013dfd21d7680001', '口音', 'toneCode', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000015', '8a81876b3dfd20a3013dfd21d7680001', '口音描述', 'tone', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000016', '8a81876b3dfd20a3013dfd21d7680001', '户籍地代码', 'door', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000017', '8a81876b3dfd20a3013dfd21d7680001', '现住址代码', 'address', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000018', '8a81876b3dfd20a3013dfd21d7680001', '特殊身份', 'specialIdentityCode', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000019', '8a81876b3dfd20a3013dfd21d7680001', '特殊人群', 'specialGroupCode', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000020', '8a81876b3dfd20a3013dfd21d7680001', '经济来源', 'sourceIncomeCode', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000021', '8a81876b3dfd20a3013dfd21d7680001', '职业', 'jobCode', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000022', '8a81876b3dfd20a3013dfd21d7680001', '职务', 'headShip', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000023', '8a81876b3dfd20a3013dfd21d7680001', '工作单位', 'employUnit', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000024', '8a81876b3dfd20a3013dfd21d7680001', '工作单位详址', 'employAddress', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000025', '8a81876b3dfd20a3013dfd21d7680001', '案件名称', 'caseName', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000026', '8a81876b3dfd20a3013dfd21d7680001', '案件类别', 'caseClasses', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000027', '8a81876b3dfd20a3013dfd21d7680001', '案由', 'reason', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000030', '8a81876b3dfd20a3013dfd21d7680001', '户籍地地址', 'doorDetail', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000031', '8a81876b3dfd20a3013dfd21d7680001', '户籍地乡镇/街道/社区', 'doorStreet', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000032', '8a81876b3dfd20a3013dfd21d7680001', '现住址地址', 'addressDetail', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000033', '8a81876b3dfd20a3013dfd21d7680001', '现住址乡镇/街道/社区', 'addressStreet', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000034', '8a81876b3dfd20a3013dfd21d7680001', '出生地代码', 'birthCode', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000035', '8a81876b3dfd20a3013dfd21d7680001', '出生地地址', 'birthDetail', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000036', '8a81876b3dfd20a3013dfd21d7680001', '出生地乡镇/街道/社区', 'birthStreet', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000042', '8a81876b3dfd20a3013dfd21d7680001', '签发地点', 'visaPlace', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000041', '8a81876b3dfd20a3013dfd21d7680001', '有效期', 'passportValidDateST', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000040', '8a81876b3dfd20a3013dfd21d7680001', '签发日期', 'visaDateST', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000039', '8a81876b3dfd20a3013dfd21d7680001', '国家码', 'CountryCode', '{"required":false}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000038', '8a81876b3dfd20a3013dfd21d7680001', '护照类型', 'passportType', '{"required":true}');
insert into GAFIS_GATHER_FIELDSET (pk_id, node_id, field_name, field, rule)
values ('00100000000000000000000000000037', '8a81876b3dfd20a3013dfd21d7680001', '护照号', 'passportNum', '{"required":true}');
