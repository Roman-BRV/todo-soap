package com.softserveinc.todosoap.rest;

import com.softserveinc.todosoap.service.ExportTodosService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TodosExportController.class)
@WebAppConfiguration
class TodosExportControllerTest {

	@MockBean
	private ExportTodosService exportTodosService;

	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void claimExportShouldReturnRequest201WithClaimIdStringContentOnRightRequest() throws Exception {
		String uri = "/todos/exports";
		String claimId = "claim_id";

		when(exportTodosService.claimExport()).thenReturn(claimId);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)).andReturn();

		int responseStatus = mvcResult.getResponse().getStatus();
		String responseData = mvcResult.getResponse().getContentAsString();
		assertEquals(201, responseStatus);
		assertEquals(claimId, responseData);
	}

	@Test
	void claimExportShouldReturnRequest500WithEmptyStringContentWhenAnyExceptionOccur() throws Exception {
		String uri = "/todos/exports";

		when(exportTodosService.claimExport()).thenThrow(new RuntimeException());
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)).andReturn();

		int responseStatus = mvcResult.getResponse().getStatus();
		String responseData = mvcResult.getResponse().getContentAsString();
		assertEquals(500, responseStatus);
		assertEquals("", responseData);
	}

	@Test
	void checkStatusShouldReturnRequest200WithClaimStatusStringContentOnRightRequest() throws Exception {
		String uri = "/todos/exports/claim_id/status";
		String claimStatus = "COMPLETED";

		when(exportTodosService.checkStatus(any())).thenReturn(claimStatus);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int responseStatus = mvcResult.getResponse().getStatus();
		String responseData = mvcResult.getResponse().getContentAsString();
		assertEquals(200, responseStatus);
		assertEquals(claimStatus, responseData);
	}

	@Test
	void checkStatusShouldReturnRequest404WithEmptyStringContentWhenSuchClaimIdNotExist() throws Exception {
		String uri = "/todos/exports/claim_id/status";

		when(exportTodosService.checkStatus(any())).thenThrow(new NoSuchElementException());
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int responseStatus = mvcResult.getResponse().getStatus();
		String responseData = mvcResult.getResponse().getContentAsString();
		assertEquals(404, responseStatus);
		assertEquals("", responseData);
	}

	@Test
	void checkStatusShouldReturnRequest500WithEmptyStringContentWhenIdExistButExceptionOccur() throws Exception {
		String uri = "/todos/exports/claim_id/status";

		when(exportTodosService.checkStatus(any())).thenThrow(new RuntimeException());
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int responseStatus = mvcResult.getResponse().getStatus();
		String responseData = mvcResult.getResponse().getContentAsString();
		assertEquals(500, responseStatus);
		assertEquals("", responseData);
	}

	@Test
	void downloadFileShouldReturnRequest200WithClaimStatusStringContentOnRightRequest() throws Exception {
		String uri = "/todos/exports/claim_id/file";
		String filePath = "file_path";

		when(exportTodosService.downloadFileLink(any())).thenReturn(filePath);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int responseStatus = mvcResult.getResponse().getStatus();
		String responseData = mvcResult.getResponse().getContentAsString();
		assertEquals(200, responseStatus);
		assertEquals(filePath, responseData);
	}

	@Test
	void downloadFileShouldReturnRequest404WithEmptyStringContentWhenSuchClaimIdNotExist() throws Exception {
		String uri = "/todos/exports/claim_id/file";

		when(exportTodosService.downloadFileLink(any())).thenThrow(new NoSuchElementException());
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int responseStatus = mvcResult.getResponse().getStatus();
		String responseData = mvcResult.getResponse().getContentAsString();
		assertEquals(404, responseStatus);
		assertEquals("", responseData);
	}

	@Test
	void downloadFileShouldReturnRequest500WithEmptyStringContentWhenIdExistButExceptionOccur() throws Exception {
		String uri = "/todos/exports/claim_id/file";

		when(exportTodosService.downloadFileLink(any())).thenThrow(new RuntimeException());
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

		int responseStatus = mvcResult.getResponse().getStatus();
		String responseData = mvcResult.getResponse().getContentAsString();
		assertEquals(500, responseStatus);
		assertEquals("", responseData);
	}
}
