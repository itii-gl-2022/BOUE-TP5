package fr.webapp.j3;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
/**
 * Servlet implementation class SumServlet
 */
@WebServlet("*.do")
public class SumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String code = request.getParameter("code"); 
        String name = request.getParameter("name"); 
		response.getWriter().append("Bienvenue").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		//Create the barcode bean
		Code39Bean bean = new Code39Bean();

		final int dpi = 150;

		//Configure the barcode generator
		bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi)); //makes the narrow bar 
		                                                 //width exactly one pixel
		bean.setWideFactor(3);
		bean.doQuietZone(false);

		//Open output file
		File outputFile = new File("qrcode.png");
		OutputStream out = new FileOutputStream(outputFile);
		try {
		    BitmapCanvasProvider canvas = new BitmapCanvasProvider(
		            out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
	        String chaine = request.getParameter("nomproduit")+request.getParameter("idproduit");
		    bean.generateBarcode(canvas, chaine);
		    canvas.finish();
		} finally {
		    out.close();
		}
	}

}
