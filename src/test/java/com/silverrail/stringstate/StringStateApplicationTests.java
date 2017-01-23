package com.silverrail.stringstate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Use this to allow us to control the order of the tests and check that the data is being stored
public class StringStateApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Autowired
    private MockMvc mvc;

	/**
	 * Test the calculation works
	 */
    @Test
    public void cgetSum() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getSum?stringValue=nnhf456kn2ll45").header("User-Agent", "HtmlUnit").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"characters\":null,\"amount\":503}")));
    }
    
	/**
	 * Test the extracting from the repo works
	 */
    @Test
    public void bgetState() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getState?stringValue=nnhf456kn2ll45").header("User-Agent", "HtmlUnit").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"characters\":\"nhd45lk29\",\"amount\":0}")));
    }
    
	/**
	 * Save the data to the repo
	 */
    @Test
    public void asaveState() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/saveState").header("User-Agent", "HtmlUnit").content("{\"characters\" : \"nhd45lk29\",\"amount\" : 1}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"characters\":\"nhd45lk29\",\"amount\":1}")));
    }
	
	/**
	 * Check that we get a change of data
	 */
	@Test
    public void dgetStateAfterSave() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getState").header("User-Agent", "HtmlUnit").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"characters\":\"nhd45lk29\",\"amount\":0}")));
    }
	
	/**
	 * Test that a second browser works
	 */
	@Test
    public void aasaveState() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/saveState").header("User-Agent", "HtmlUnit2").content("{\"characters\" : \"nhd45lk29\",\"amount\" : 2}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"characters\":\"nhd45lk29\",\"amount\":2}")));
    }
	
	/**
	 * The data from the second browser
	 */
	@Test
    public void abgetState() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getState?stringValue=nnhf456kn2ll45").header("User-Agent", "HtmlUnit2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"characters\":\"nhd45lk29nhd45lk29\",\"amount\":0}")));
    }
	
	/**
	 * Save the data to the repo with a bad character and check that we receive a descriptive 400 error
	 */
    @Test
    public void errorSaveState() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/saveState").header("User-Agent", "HtmlUnit").content("{\"characters\" : \"nhd45$lk29\",\"amount\" : 1}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
    
    //The following represent 3 edge cases

    /**
     * Tests the maximum total value allowed - assuming we are currently using integer
     * @throws Exception
     */
    @Test
    public void edge1() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/getSum?stringValue=nnhf2kn2147483645").header("User-Agent", "HtmlUnit").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"characters\":null,\"amount\":2147483647}")));
    }
    
    /**
     * Tests the lowest allowed that is not a negative value
     * @throws Exception
     */
    @Test
    public void edge2() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.get("/getSum?stringValue=nnhf0kn00hg0mhg0").header("User-Agent", "HtmlUnit").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("{\"characters\":null,\"amount\":0}")));
    }
    
    /**
     * Tests that no value is acceptable
     * @throws Exception
     */
    @Test
    public void edge3() throws Exception {
    	mvc.perform(MockMvcRequestBuilders.post("/saveState").header("User-Agent", "HtmlUnit2").content("{\"characters\" : \"\",\"amount\" : 0}").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(equalTo("{\"characters\":\"\",\"amount\":0}")));
    }
    
    
}
