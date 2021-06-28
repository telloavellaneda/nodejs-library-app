/*
 * Copyright (C) 2017 ashleyheyeral
 *
 * The SOFTWARE PRODUCT includes entitlement to limited technical support services  
 * support services
 * SOFTWARE PRODUCT will perform substantially in accordance with 
 * the accompanying written materials.
 */
package mx.com.eai.tpp.service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.com.eai.tpp.form.DetalleSolicitudForm;
import mx.com.eai.tpp.model.subclases.DetalleMaterialImagen;
import mx.com.eai.tpp.util.Constant;
import mx.com.eai.tpp.util.Util;
import org.springframework.stereotype.Service;


/* *****************************************************************************
 * @author Ashley Heyeral Ruiz Fernandez  22-nov-2017 19:59:04  Description: 
 * GenerarPDFResultadoLaboratorioImpl.java  
 * *****************************************************************************
 */
@Service
public class GenerarPDFSolicitudImpl implements GenerarPDFSolicitud
{
    private PdfPCell cell;
    private PdfPTable table;
    private ServletContext servletContext;

    class TableHeader extends PdfPageEventHelper
    {

        /**
         * The header text.
         */
        String header;
        /**
         * The template with the total number of pages.
         */
        PdfTemplate total;

        /**
         * Allows us to change the content of the header.
         *
         * @param header The new header String
         */
        public void setHeader(String header)
        {
            this.header = header;
        }

        /**
         * Creates the PdfTemplate that will hold the total number of pages.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onOpenDocument(PdfWriter writer, Document document)
        {
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        /**
         * Adds a header to every page
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onEndPage(PdfWriter writer, Document document)
        {
            try
            {

                Font fontTitulo = new Font();
                fontTitulo.setSize(11);
                fontTitulo.setColor(new BaseColor(0, 89, 138));
                fontTitulo.setStyle(Font.BOLD);

                Font fontDefault = new Font();
                fontDefault.setSize(10);
                fontDefault.setColor(new BaseColor(0, 89, 138));

                PdfPTable table = new PdfPTable(2);
                table.setWidths(new int[]
                {
                    30, 70
                });
                table.setTotalWidth(580);

                PdfPCell cell;
                InputStream logo = servletContext.getResourceAsStream("/resources/ultima-layout/images/TPP.png");
                BufferedImage bf = ImageIO.read(logo);
                Image image = Image.getInstance(bf, null);

                image.scaleAbsolute(156f, 48f);
                cell = new PdfPCell(image);
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("SOLICITUD DE ACCESO A TERMINALES PORTUARIAS DEL PACÍFICO S.A.P.I. DE C.V.", fontTitulo));
                cell.setBorder(0);
                cell.setPaddingTop(10);
                table.addCell(cell);
                table.writeSelectedRows(0, -1, 25, 780, writer.getDirectContent());

                table = new PdfPTable(3);
                table.setWidths(new int[]
                {
                    85, 10, 5
                });
                table.setTotalWidth(580);
                cell = new PdfPCell(new Phrase("TERMINALES PORTUARIAS DEL PACÍFICO S.A.P.I DE C.V.\n"
                        + "CARRETERA MÉXICO-TOLUCA 4000, CUAJIMALPA, C.P. 05000, CDMX, MÉXICO", fontDefault));
                cell.setBorder(0);
                table.addCell(cell);

                logo = servletContext.getResourceAsStream("/resources/ultima-layout/images/certificado.png");
                bf = ImageIO.read(logo);
                image = Image.getInstance(bf, null);
                image.scaleAbsolute(40f, 40f);
                cell = new PdfPCell(image);
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(Image.getInstance(total));
                cell.setBorder(0);
                table.addCell(cell);
                table.writeSelectedRows(0, -1, 25, 55, writer.getDirectContent());
            }
            catch (Exception de)
            {
                throw new ExceptionConverter(de);
            }
        }

        /**
         * Fills out the total number of pages before the document is closed.
         *
         * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
         * com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
         */
        public void onCloseDocument(PdfWriter writer, Document document)
        {
            Font fontDefault = new Font();
            fontDefault.setSize(10);
            fontDefault.setColor(new BaseColor(0, 89, 138));
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1), fontDefault),
                    2, 5, 0);
        }
    }

    public void generarPDF(DetalleSolicitudForm detalleSolicitudForm, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        this.servletContext = request.getSession().getServletContext();
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        document.setPageSize(PageSize.LETTER);
        document.setMargins(25, 25, 50, 60);
        TableHeader event = new TableHeader();
        writer.setPageEvent(event);
        document.open();

        Font negritas = new Font();
        negritas.setStyle(Font.BOLD);
        negritas.setSize(10);
        Font negritasMini = new Font();
        negritasMini.setStyle(Font.BOLD);
        negritasMini.setSize(8);
        Font fontDefault = new Font();
        fontDefault.setSize(10);
        Font fontTitulo = new Font();
        fontTitulo.setSize(11);
        fontTitulo.setColor(new BaseColor(0, 89, 138));
        fontTitulo.setStyle(Font.BOLD);

        table = new PdfPTable(new float[]
        {
            20, 28, 12, 40
        });
        table.setWidthPercentage(100);
        table.getDefaultCell().setBorder(0);

        switch (detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid())
        {
            case Constant.ID_TIPO_SOLICITUD_PERSONAL:
                cell = new PdfPCell(new Phrase("PERSONAL", fontTitulo));
                break;
            case Constant.ID_TIPO_SOLICITUD_VEHICULO:
                cell = new PdfPCell(new Phrase("VEHICULO", fontTitulo));
                break;
            case Constant.ID_TIPO_SOLICITUD_MATERIAL:
                cell = new PdfPCell(new Phrase("MATERIAL", fontTitulo));
                break;
        }
        cell.setColspan(4);
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("( " + detalleSolicitudForm.getSolicitud().getEstatussolicitudid().getDescripcion() + " )", negritas));
        cell.setColspan(4);
        cell.setBorder(0);
        cell.setFixedHeight(25);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Folio de la solicitud:", negritas));
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getSolicitudid().toString(), negritas));
        cell.setColspan(3);
        cell.setBorder(0);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Vigencia acceso:", negritas));
        cell.setBorder(0);
        table.addCell(cell);
        if (detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_MATERIAL)
        {
            cell = new PdfPCell(new Phrase(Util.formatearFecha(detalleSolicitudForm.getSolicitud().getFechainicio()), negritas));
        }
        else
        {
            cell = new PdfPCell(new Phrase(Util.formatearFecha(detalleSolicitudForm.getSolicitud().getFechainicio()) + " – " + Util.formatearFecha(detalleSolicitudForm.getSolicitud().getFechafin()), negritas));
        }
        cell.setColspan(3);
        cell.setBorder(0);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Usuario:", negritas));
        cell.setBorder(0);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getPersid().getNombre() + " "
                + detalleSolicitudForm.getSolicitud().getPersid().getApaterno() + " "
                + detalleSolicitudForm.getSolicitud().getPersid().getAmaterno(), fontDefault));
        cell.setColspan(3);
        cell.setBorder(0);
        table.addCell(cell);

        if (detalleSolicitudForm.getSolicitud().getEstatussolicitudid().getEstatussolicitudid() != Constant.ID_ESTATUS_SOLICITUD_PENDIENTE)
        {
            cell = new PdfPCell(new Phrase("Fecha de registro:", negritas));
            cell.setBorder(0);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(Util.formatearFecha(detalleSolicitudForm.getSolicitud().getFecharegistro(), Util.FORMATODDMMYYYYHHMMSS), fontDefault));
            cell.setColspan(3);
            cell.setBorder(0);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Fecha de aprobación:", negritas));
            cell.setBorder(0);
            cell.setFixedHeight(50);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(Util.formatearFecha(detalleSolicitudForm.getUltimaRevisionSolicitud().getFecharevision(), Util.FORMATODDMMYYYYHHMMSS), fontDefault));
            cell.setColspan(3);
            cell.setBorder(0);
            cell.setFixedHeight(50);
            table.addCell(cell);
        }
        else
        {
            cell = new PdfPCell(new Phrase("Fecha de registro:", negritas));
            cell.setBorder(0);
            cell.setFixedHeight(50);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(Util.formatearFecha(detalleSolicitudForm.getSolicitud().getFecharegistro(), Util.FORMATODDMMYYYYHHMMSS), fontDefault));
            cell.setColspan(3);
            cell.setBorder(0);
            cell.setFixedHeight(50);
            table.addCell(cell);
        }

        switch (detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid())
        {
            case Constant.ID_TIPO_SOLICITUD_PERSONAL:
                cell = new PdfPCell(new Phrase("DATOS PERSONALES", negritas));
                cell.setBorder(0);
                cell.setColspan(4);
                cell.setFixedHeight(20);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Nombre:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getPersonalid().getNombre() + " "
                        + detalleSolicitudForm.getSolicitud().getDdetallepersonal().getPersonalid().getApaterno() + " "
                        + detalleSolicitudForm.getSolicitud().getDdetallepersonal().getPersonalid().getAmaterno(), fontDefault));
                cell.setColspan(3);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Nacionalidad:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getPersonalid().getExtranjero() == 1 ? "Mexicana" : "Extranjera", fontDefault));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getPersonalid().getExtranjero() == 1 ? "CURP:" : "Pasaporte:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getPersonalid().getCurp(), fontDefault));
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Tipo Sanguíneo:", negritas));
                cell.setBorder(0);
                cell.setFixedHeight(40);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getPersonalid().getTiposanguineo(), fontDefault));
                cell.setColspan(3);
                cell.setBorder(0);
                cell.setFixedHeight(40);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("DATOS DEL SEGURO MÉDICO", negritas));
                cell.setBorder(0);
                cell.setColspan(4);
                cell.setFixedHeight(20);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Tipo de seguro:", negritas));
                cell.setBorder(0);

                if (!detalleSolicitudForm.getSolicitud().getDdetallepersonal().getTiposeguro().equals("Seguro de Gastos Médicos"))
                {
                    cell.setFixedHeight(40);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getTiposeguro(), fontDefault));
                    cell.setBorder(0);
                    cell.setFixedHeight(40);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase("Número:", negritas));
                    cell.setBorder(0);
                    cell.setFixedHeight(40);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getNumseguro(), fontDefault));
                    cell.setBorder(0);
                    cell.setFixedHeight(40);
                    table.addCell(cell);
                }
                else
                {
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getTiposeguro(), fontDefault));
                    cell.setBorder(0);
                    cell.setColspan(3);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase("Póliza:", negritas));
                    cell.setBorder(0);
                    cell.setFixedHeight(40);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getNumseguro(), fontDefault));
                    cell.setBorder(0);
                    cell.setFixedHeight(40);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase("Vigencia:", negritas));
                    cell.setBorder(0);
                    cell.setFixedHeight(40);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(Util.formatearFecha(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getVigenciaseguro()), fontDefault));
                    cell.setBorder(0);
                    cell.setFixedHeight(40);
                    table.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("DATOS DE LA SOLICITUD", negritas));
                cell.setBorder(0);
                cell.setColspan(4);
                cell.setFixedHeight(20);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Empresa:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getPersonalid().getEmpresa(), fontDefault));
                cell.setColspan(3);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Actividad específica a realizar", negritas));
                cell.setColspan(4);
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getActividad(), fontDefault));
                cell.setColspan(4);
                cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                cell.setBorder(0);
                cell.setFixedHeight(20);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("¿Requiere acceso de chofer?:", negritas));
                cell.setBorder(0);
                cell.setColspan(2);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getRequierechofer() == 1 ? "Sí" : "No", fontDefault));
                cell.setBorder(0);
                cell.setColspan(2);
                table.addCell(cell);

                if (detalleSolicitudForm.getSolicitud().getDdetallepersonal().getRequierechofer() == 1)
                {
                    cell = new PdfPCell(new Phrase("Vigencia de la licencia:", negritas));
                    cell.setBorder(0);
                    cell.setColspan(2);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(Util.formatearFecha(detalleSolicitudForm.getSolicitud().getDdetallepersonal().getVigencialicenciamanejo()), fontDefault));
                    cell.setBorder(0);
                    cell.setColspan(2);
                    table.addCell(cell);
                }

                break;
            case Constant.ID_TIPO_SOLICITUD_VEHICULO:
            case Constant.ID_TIPO_SOLICITUD_MATERIAL:
                if (detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_MATERIAL)
                {
                    cell = new PdfPCell(new Phrase("DATOS DE LOS MATERIALES", negritas));
                    cell.setBorder(0);
                    cell.setColspan(4);
                    cell.setFixedHeight(20);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Motivo de ingreso:", negritas));
                    cell.setBorder(0);

                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getMotivoingresoid().getDescripcion(), fontDefault));
                    cell.setColspan(3);
                    cell.setBorder(0);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Tipo de ingreso:", negritas));
                    cell.setBorder(0);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getTipoentrada() == 1 ? "Entrada" : (detalleSolicitudForm.getSolicitud().getTipoentrada() == 1 ? "Salida" : "Entrada y salida"), fontDefault));
                    cell.setColspan(3);
                    cell.setBorder(0);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Uso:", negritas));
                    cell.setBorder(0);
                    cell.setFixedHeight(20);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getUso(), fontDefault));
                    cell.setColspan(3);
                    cell.setBorder(0);
                    cell.setFixedHeight(20);
                    table.addCell(cell);

                    cell = new PdfPCell(new Phrase("Listado de Materiales", negritas));
                    cell.setColspan(4);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);

                    for (DetalleMaterialImagen mat : detalleSolicitudForm.getMateriales())
                    {
                        cell = new PdfPCell(new Phrase(mat.getMaterial().getCantidad() + " " + mat.getMaterial().getDescripcion(), negritas));
                        cell.setColspan(4);
                        cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("Marca:", negritas));
                        cell.setBorder(Rectangle.LEFT);
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(mat.getMaterial().getMarca(), fontDefault));
                        cell.setBorder(0);
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase("Modelo:", negritas));
                        cell.setBorder(0);
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(mat.getMaterial().getModelo(), fontDefault));
                        cell.setBorder(Rectangle.RIGHT);
                        table.addCell(cell);

                        cell = new PdfPCell(new Phrase("Serie:", negritas));
                        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(mat.getMaterial().getNumserie(), fontDefault));
                        cell.setBorder(Rectangle.BOTTOM);
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase("Propietario:", negritas));
                        cell.setBorder(Rectangle.BOTTOM);
                        table.addCell(cell);
                        cell = new PdfPCell(new Phrase(mat.getMaterial().getPropietario(), fontDefault));
                        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                        table.addCell(cell);
                    }
                    cell = new PdfPCell(new Phrase(" ", negritas));
                    cell.setColspan(4);
                    cell.setBorder(0);
                    table.addCell(cell);
                }
                cell = new PdfPCell(new Phrase("DATOS DEL VEHÍCULO", negritas));
                cell.setBorder(0);
                cell.setColspan(4);
                cell.setFixedHeight(20);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Tipo de vehículo:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getVehiculoid().getTipo() == 1 ? "Personal" : "Oficial", fontDefault));
                cell.setBorder(0);

                if (detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid() == 3 || detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getVehiculoid().getTipo() == 1)
                {
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase("Conductor:", negritas));
                    cell.setBorder(0);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getNombreconductor(), fontDefault));
                    cell.setBorder(0);
                    table.addCell(cell);
                }
                else
                {
                    cell.setColspan(3);
                    table.addCell(cell);
                }

                cell = new PdfPCell(new Phrase("Empresa:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getEmpresa(), fontDefault));
                cell.setBorder(0);
                cell.setColspan(3);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Marca:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getVehiculoid().getMarca(), fontDefault));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Modelo:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getVehiculoid().getModelo(), fontDefault));
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Color:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getVehiculoid().getColor(), fontDefault));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Placa:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getVehiculoid().getPlaca(), fontDefault));
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Descripción:", negritas));
                cell.setBorder(0);
                cell.setFixedHeight(40);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getVehiculoid().getDescripcion(), fontDefault));
                cell.setBorder(0);
                cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                cell.setColspan(3);
                cell.setFixedHeight(40);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("DATOS DEL SEGURO", negritas));
                cell.setBorder(0);
                cell.setColspan(4);
                cell.setFixedHeight(20);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase("Póliza:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getNumpoliza(), fontDefault));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Vigencia:", negritas));
                cell.setBorder(0);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(Util.formatearFecha(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getVigenciapoliza()), fontDefault));
                cell.setBorder(0);
                table.addCell(cell);
                if (detalleSolicitudForm.getSolicitud().getTiposolicitudid().getTiposolicitudid() == Constant.ID_TIPO_SOLICITUD_VEHICULO)
                {
                    cell = new PdfPCell(new Phrase("Motivo de ingreso:", negritas));
                    cell.setBorder(0);
                    table.addCell(cell);
                    cell = new PdfPCell(new Phrase(detalleSolicitudForm.getSolicitud().getDdetallevehiculo().getMotivoingreso(), fontDefault));
                    cell.setBorder(0);
                    cell.setColspan(3);
                    table.addCell(cell);
                }
                break;
        }

        document.add(table);

        document.close();
    }
}
