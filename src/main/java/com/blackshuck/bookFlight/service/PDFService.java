package com.blackshuck.bookFlight.service;

import com.blackshuck.bookFlight.entity.Ticket;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFService
{

    public byte[] generateTicketPdf(List<Ticket> tickets)
    {
        try
        {
            Document document = new Document(PageSize.A5.rotate());

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("Tickets List"));

            document.add(new Paragraph("-----------"));

            for (Ticket t : tickets)
            {
                PdfPTable layout = new PdfPTable(2);

                layout.setWidthPercentage(100);

                layout.setSpacingBefore(20f);

                layout.setWidths(new float[]{3f, 1.5f});

                PdfPTable infoTable = new PdfPTable(1);

                infoTable.setWidthPercentage(100);

                Font labelFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.DARK_GRAY);

                Font valueFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);

                infoTable.addCell(makeCell("Passenger: " + t.getPassengerId().getFirstName()
                        + " " + t.getPassengerId().getLastName(), labelFont));

                infoTable.addCell(makeCell("\nOrigin: " + t.getFlightId().getOrigin(), valueFont));

                infoTable.addCell(makeCell("\nDestination: " + t.getFlightId().getDestination(), valueFont));

                infoTable.addCell(makeCell("\nOrigin Airport: " + t.getFlightId().getOriginAirportId().getName(), valueFont));

                infoTable.addCell(makeCell("\nDestination Airport: " +
                        t.getFlightId().getDestinationAirportId().getName(), valueFont));

                infoTable.addCell(makeCell("\nAirline: " + t.getFlightId().getAirlineId().getName(), valueFont));

                infoTable.addCell(makeCell("\nDeparture Date: " + t.getFlightId().getDepartureDate(), valueFont));

                infoTable.addCell(makeCell("\nTake-off Time: " + t.getFlightId().getTakeoffTime(), valueFont));

                infoTable.addCell(makeCell("\nLanding Time: " + t.getFlightId().getLandingTime(), valueFont));

                infoTable.addCell(makeCell("\nSeat Number: " + t.getSeatNumber(), valueFont));

                layout.addCell(infoTable);

                try {
                    BufferedImage qrBuffered = generateQrImage("Passenger: " + t.getPassengerId().getFirstName()
                            + " " + t.getPassengerId().getLastName() +
                            "Origin: " + t.getFlightId().getOrigin() +
                            "Destination: " + t.getFlightId().getDestination() +
                            "Origin Airport: " + t.getFlightId().getOriginAirportId().getName() +
                            "Destination Airport: " + t.getFlightId().getDestinationAirportId().getName() +
                            "Airline: " + t.getFlightId().getAirlineId().getName() +
                            "Departure Date: " + t.getFlightId().getDepartureDate() +
                            "Take-off Time: " + t.getFlightId().getTakeoffTime() +
                            "Landing Time: " + t.getFlightId().getLandingTime() +
                            "Seat Number: " + t.getSeatNumber());

                    try
                    {
                        Image qrImage = convertToPdfImage(qrBuffered);

                        qrImage.setAlignment(Image.ALIGN_CENTER);

                        layout.addCell(qrImage);
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
                catch (WriterException e)
                {
                    throw new RuntimeException(e);
                }

                document.add(new LineSeparator());

                document.add(layout);
            }

            document.close();

            return out.toByteArray();
        }
        catch (DocumentException documentException)
        {
            throw new RuntimeException("PDF generating failed", documentException);
        }
    }

    private PdfPCell makeCell(String text, Font font)
    {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setBorder(Rectangle.NO_BORDER);

        cell.setPaddingBottom(6f);

        return cell;
    }

    public BufferedImage generateQrImage(String text) throws WriterException
    {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 150, 150);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public Image convertToPdfImage(BufferedImage bufferedImage) throws IOException, BadElementException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(bufferedImage, "png", baos);

        baos.flush();

        Image image = Image.getInstance(baos.toByteArray());

        baos.close();

        return image;
    }

}
