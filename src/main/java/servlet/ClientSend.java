
package servlet;

import model.Index;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 * Handles Client's index update request
 */
@WebServlet("/ClientSend")
public class ClientSend extends HttpServlet {

    private static final long serialVersionUID = -8314035702649252239L;
    public static final String UPDATE = "Update";

    //test
	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(mappedName = "java:/queue/test")
	private Queue queue;
	
	@Resource(mappedName = "java:/topic/test")
	private Topic topic;

    /**
     * Handles <code>GET</code> request
     * @param req request from client
     * @param resp response to client
     * @throws ServletException
     * @throws IOException
     */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		

		
		//req.isUserInRole("guest");
        StringBuilder out = new StringBuilder();
		Connection connection = null;
		
		try {
		    Destination destination;
		    //if (req.getParameterMap().keySet().contains("topic")) {
		    //    destination = topic;
		   // } else {
		        destination = queue;
		    //}
		    out.append("<H2>Sending messages to <em>" + destination + "</em></H2><br/><br/><br/>");
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			MessageProducer messageProducer = session.createProducer(destination);
			connection.start();
			
			Index index = new Index();
			
			index.setId(req.getParameter("index"));
			index.setCurrentValue(new BigDecimal(req.getParameter("Value")));
	
			ObjectMessage mymsg = session.createObjectMessage(index);
			
			messageProducer.send(mymsg);
			
			out.append("<p>Sent update for index: " + index.getId() + ". New Value is: " + index.getCurrentValue() + ".</p></br>");


		} catch (JMSException e) {
			e.printStackTrace();
			out.append("<h2><br/><br/><br/>A problem occurred during the delivery of this message</h2>");
			out.append("<br/><br/><br/>");
			out.append("<p><i>Go your the JBoss Application Server console or Server log to see the error stack trace</i></p>");
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			req.setAttribute("title", UPDATE);
            req.setAttribute("content", out.toString());
            RequestDispatcher view = req.getRequestDispatcher("Template.jsp");
            view.forward(req, resp);
		}
	}

    /**
     * Handles <code>POST</code> request
     * @param req request from client
     * @param resp response to client
     * @throws ServletException
     * @throws IOException
     */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
