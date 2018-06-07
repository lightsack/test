package com.fss.fsswms.mvc.classic.cmn.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.fss.fsswms.base.data.Box;
import com.fss.fsswms.base.persistence.CmnConstants;
import com.fss.fsswms.base.util.SessionUtil;
import com.fss.fsswms.mvc.classic.cmn.vo.user.UserVO;

@Service
public class LoginService {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private HttpServletRequest request;
	
	@Value("#{config['session.timeout']}")
	private int sessionTimeout;
	
	
	public void loginProc(Box paramBox, ModelMap model) throws Exception {
		HttpSession session = request.getSession();
		if(!session.isNew()) {
			SessionUtil.invalidate(session);
	        session = request.getSession();
//	        log.debug(" session not new >>> {}", SessionUtil.getUser(session));
		}

		UserVO userVO = sqlSession.selectOne("classic.cmn.login.selectUser", paramBox);
		if (null != userVO) {

			if (paramBox.getString("psWd").equals(userVO.getPsWd())) {
//			if (cryptoService.encrypt(paramBox.getString("psWd")).equals(userVO.getPsWd())) {				
				List<Box> userProgAuthList = sqlSession.selectList("classic.cmn.login.selectUserProgAuthList", userVO);
				SessionUtil.setUser(session, userVO);
				SessionUtil.setUserProgAuthList(session, userProgAuthList);
				List<Box> codeList = sqlSession.selectList("classic.cmn.code.selectCodeList", new Box());
				SessionUtil.setCodeList(session, codeList);
				session.setMaxInactiveInterval(sessionTimeout*60+5);
				model.put("loginYn", "Y");
				model.put("userInfo", userVO);
			} else {
				model.put("loginYn", "N");
				model.put("error", CmnConstants.LOGIN_PASSWORD_NOT_EQUAL);
			}
		} else {
			model.put("loginYn", "N");
			model.put("error", CmnConstants.LOGIN_NO_ID);
		}
	}

	public void logout() throws Exception {
		HttpSession session = request.getSession();
		SessionUtil.invalidate(session);
	}
	
}