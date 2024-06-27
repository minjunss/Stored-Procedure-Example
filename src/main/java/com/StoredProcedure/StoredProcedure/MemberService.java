package com.StoredProcedure.StoredProcedure;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/** MySQL 프로시저 저장 방법:
    1. DELIMITER //         구분기호를 '//'로 일시적으로 수정
    2. 프로시저 쿼리 등록
       예시:
       CREATE PROCEDURE {프로시저명} (IN {입력받을파라미터명} {타입})
       BEGIN
       {실행할 쿼리};         -- 여기서 ;를 사용해야 하기 때문에 구분기호를 수정해줌, 입력받을 파라미터 명과 조건문에 입력할 컬럼명이 같으면 오류 발생할 수 있음
       END //
    3. DELIMITER ;          구분기호를 다시 ';'로 원상복구
 */

@Service
public class MemberService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall getMemberDetailsCall;
    private SimpleJdbcCall saveMemberCall;

    @PostConstruct
    public void init() {
        getMemberDetailsCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("GetMemberDetails")
                .declareParameters(
                        new SqlParameter("memberId", Types.INTEGER),
                        new SqlOutParameter("name", Types.VARCHAR),
                        new SqlOutParameter("email", Types.VARCHAR),
                        new SqlOutParameter("birthday", Types.VARCHAR)
                );

        saveMemberCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("saveMember")
                .declareParameters(
                        new SqlParameter("p_name", Types.NVARCHAR),
                        new SqlParameter("p_email", Types.NVARCHAR),
                        new SqlParameter("p_birthday", Types.NVARCHAR)
                );
    }

    public Map<String, Object> getMemberDetails(int memberId) {
        return getMemberDetailsCall.execute(memberId);
    }

    public void saveMember(SaveMemberDto saveMemberDto) {
        Map<String, Object> params = new HashMap<>();
        params.put("p_name", saveMemberDto.getName());
        params.put("p_email", saveMemberDto.getEmail());
        params.put("p_birthday", saveMemberDto.getBirthday());

        saveMemberCall.execute(params);
    }
}
