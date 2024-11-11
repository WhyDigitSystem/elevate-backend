package com.ebooks.elevate.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebooks.elevate.common.CommonConstant;
import com.ebooks.elevate.common.UserConstants;
import com.ebooks.elevate.dto.DocumentTypeDTO;
import com.ebooks.elevate.dto.DocumentTypeMappingDTO;
import com.ebooks.elevate.dto.ResponseDTO;
import com.ebooks.elevate.entity.DocumentTypeMappingVO;
import com.ebooks.elevate.entity.DocumentTypeVO;
import com.ebooks.elevate.service.DocumentTypeService;


@RestController
@RequestMapping("/api/documentType")
public class DocumentTypeController extends BaseController{
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeController.class);
	
	
	@Autowired
	DocumentTypeService documentTypeService;
	
	
	
	
	// Document type
	
		@PutMapping("/createUpdateDocumentType")
		public ResponseEntity<ResponseDTO> createUpdateDocumentType(@RequestBody DocumentTypeDTO documentTypeDTO) {
		    String methodName = "createUpdateDocumentType()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		    String errorMsg = null;
		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO = null;
		    try {
		        Map<String, Object> documentType1 = documentTypeService.createUpdateDocumentType(documentTypeDTO);
		        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, documentType1.get("message"));
		        responseObjectsMap.put("documentTypeVO", documentType1.get("documentTypeVO"));
		        responseDTO = createServiceResponse(responseObjectsMap);
		    } catch (Exception e) {
		        errorMsg = e.getMessage();
		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		    }
		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/documentTypeById")
		public ResponseEntity<ResponseDTO> getdocumentTypeById(@RequestParam Long id) {
			String methodName = "getdocumentTypeById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			DocumentTypeVO documentTypeVO = new DocumentTypeVO();
			try {
				documentTypeVO = documentTypeService.getDocumentTypeById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						"DocumentType information get successfully");
				responseObjectsMap.put("documentTypeVO", documentTypeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap,
						"DocumentTypeVO information receive failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/getAllDocumentType")
		public ResponseEntity<ResponseDTO> getAllDocumentType(@RequestParam Long orgid) {
			String methodName = "getAllDocumentType()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<DocumentTypeVO> documentTypeVO = new ArrayList<>();
			try {
				documentTypeVO = documentTypeService.getAllDocumentTypeByOrgId(orgid);	
				} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document Type information get successfully");
				responseObjectsMap.put("documentTypeVO", documentTypeVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Document Type information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		
		// Document Type Mapping
		
		@GetMapping("/getPendingDocumentTypeMapping")
		public ResponseEntity<ResponseDTO> getPendingDocumentTypeMapping(@RequestParam Long orgId,@RequestParam String branch,@RequestParam String branchCode,
				@RequestParam String finYear,@RequestParam String finYearIdentifier) {
			String methodName = "getPendingDocumentTypeMapping()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<Map<String, Object>> documentTypeMappingVO = new ArrayList<>();
			try {
				documentTypeMappingVO = documentTypeService.getPendingDocumentTypeMapping(orgId, branch, branchCode, finYear, finYearIdentifier);
				} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document Type information get successfully");
				responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Document Type information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/getAllDocumentTypeMapping")
		public ResponseEntity<ResponseDTO> getAllDocumentTypeMapping(@RequestParam Long orgId) {
			String methodName = "getAllDocumentTypeMapping()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			List<DocumentTypeMappingVO> documentTypeMappingVO = new ArrayList<>();
			try {
				documentTypeMappingVO = documentTypeService.getAllDocumentTypeMapping(orgId);
				} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE, "Document Type Mapping information get successfully");
				responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap, "Document Type Mapping information receive failed",
						errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}
		
		@GetMapping("/documentTypeMappingById")
		public ResponseEntity<ResponseDTO> documentTypeMappingById(@RequestParam Long id) {
			String methodName = "documentTypeMappingById()";
			LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
			String errorMsg = null;
			Map<String, Object> responseObjectsMap = new HashMap<>();
			ResponseDTO responseDTO = null;
			DocumentTypeMappingVO documentTypeMappingVO = new DocumentTypeMappingVO();
			try {
				documentTypeMappingVO = documentTypeService.getDocumentTypeMappingById(id);
			} catch (Exception e) {
				errorMsg = e.getMessage();
				LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
			}
			if (StringUtils.isBlank(errorMsg)) {
				responseObjectsMap.put(CommonConstant.STRING_MESSAGE,
						"documentTypeMappingVO information get successfully");
				responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO);
				responseDTO = createServiceResponse(responseObjectsMap);
			} else {
				responseDTO = createServiceResponseError(responseObjectsMap,
						"documentTypeMappingVO information receive failed", errorMsg);
			}
			LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
			return ResponseEntity.ok().body(responseDTO);
		}

		@PutMapping("/createDocumentTypeMapping")
		public ResponseEntity<ResponseDTO> createDocumentTypeMapping(@RequestBody DocumentTypeMappingDTO documentTypeMappingDTO) {
		    String methodName = "createDocumentTypeMapping()";
		    LOGGER.debug(CommonConstant.STARTING_METHOD, methodName);
		    String errorMsg = null;
		    Map<String, Object> responseObjectsMap = new HashMap<>();
		    ResponseDTO responseDTO = null;
		    try {
		        Map<String, Object> documentTypeMappingVO = documentTypeService.createDocumentTypeMapping(documentTypeMappingDTO);
		        responseObjectsMap.put(CommonConstant.STRING_MESSAGE, documentTypeMappingVO.get("message"));
		        responseObjectsMap.put("documentTypeMappingVO", documentTypeMappingVO.get("documentTypeMappingVO"));
		        responseDTO = createServiceResponse(responseObjectsMap);
		    } catch (Exception e) {
		        errorMsg = e.getMessage();
		        LOGGER.error(UserConstants.ERROR_MSG_METHOD_NAME, methodName, errorMsg);
		        responseDTO = createServiceResponseError(responseObjectsMap, errorMsg, errorMsg);
		    }
		    LOGGER.debug(CommonConstant.ENDING_METHOD, methodName);
		    return ResponseEntity.ok().body(responseDTO);
		}
}
