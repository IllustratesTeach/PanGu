-- Loading LogicDBRule...
insert into GAFIS_LOGIC_DB_RULE (PK_ID, LOGIC_CODE, LOGIC_NAME, LOGIC_CATEGORY, LOGIC_DELTAG, LOGIC_REMARK, LOGIC_RULE, KEYID)
values ('3', '3702', '省厅库', '0', '1', '捺印卡号以37开头,不包含3702开头', '{"head":37,"exclusive":3702}', '5');
insert into GAFIS_LOGIC_DB_RULE (PK_ID, LOGIC_CODE, LOGIC_NAME, LOGIC_CATEGORY, LOGIC_DELTAG, LOGIC_REMARK, LOGIC_RULE, KEYID)
values ('4', '3702', '重点库', '0', '1', '交换来的数据', null, '6');
insert into GAFIS_LOGIC_DB_RULE (PK_ID, LOGIC_CODE, LOGIC_NAME, LOGIC_CATEGORY, LOGIC_DELTAG, LOGIC_REMARK, LOGIC_RULE, KEYID)
values ('5', '3702', '青岛本地库', '1', '1', '案件编号以3702开头', '{"head":3702}', '7');
insert into GAFIS_LOGIC_DB_RULE (PK_ID, LOGIC_CODE, LOGIC_NAME, LOGIC_CATEGORY, LOGIC_DELTAG, LOGIC_REMARK, LOGIC_RULE, KEYID)
values ('6', '3702', '青岛外地库', '1', '1', '案件编号以非3702开头', null, '8');
insert into GAFIS_LOGIC_DB_RULE (PK_ID, LOGIC_CODE, LOGIC_NAME, LOGIC_CATEGORY, LOGIC_DELTAG, LOGIC_REMARK, LOGIC_RULE, KEYID)
values ('1', '3702', '青岛本地库', '0', '1', '捺印卡号以3702开头', '{"head":3702}', '1');
insert into GAFIS_LOGIC_DB_RULE (PK_ID, LOGIC_CODE, LOGIC_NAME, LOGIC_CATEGORY, LOGIC_DELTAG, LOGIC_REMARK, LOGIC_RULE, KEYID)
values ('2', '3702', '社会人员库', '0', '1', '捺印卡号以BA,JLRY,XCRY开头', '{"head":BA}', '2');
insert into GAFIS_LOGIC_DB_RULE (PK_ID, LOGIC_CODE, LOGIC_NAME, LOGIC_CATEGORY, LOGIC_DELTAG, LOGIC_REMARK, LOGIC_RULE, KEYID)
values ('2', '3702', '社会人员库', '0', '1', '捺印卡号以BA,JLRY,XCRY开头', '{"head":JLRY}', '3');
insert into GAFIS_LOGIC_DB_RULE (PK_ID, LOGIC_CODE, LOGIC_NAME, LOGIC_CATEGORY, LOGIC_DELTAG, LOGIC_REMARK, LOGIC_RULE, KEYID)
values ('2', '3702', '社会人员库', '0', '1', '捺印卡号以BA,JLRY,XCRY开头', '{"head":XCRY}', '4');