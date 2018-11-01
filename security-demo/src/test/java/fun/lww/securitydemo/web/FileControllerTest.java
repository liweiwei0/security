package fun.lww.securitydemo.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mock;

    @Before
    public void setup() {
        mock = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void upload() throws Exception {
        mock.perform(MockMvcRequestBuilders.multipart("/file")
                .file(new MockMultipartFile("file", "test.txt",
                        "multipart/form-data", "is file bytes".getBytes())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void download() throws Exception {
        String out = mock.perform(MockMvcRequestBuilders.get("/file/1541037234049.txt"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(out);

    }
}