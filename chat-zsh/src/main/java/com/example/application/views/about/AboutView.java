package com.example.application.views.about;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

@PageTitle("About")
@Route(value = "About", layout = MainLayout.class)
public class AboutView extends VerticalLayout {

    private HorizontalLayout layout = new HorizontalLayout();

    /**
     * Information about the system
     */
    public AboutView() {
        setSpacing(false);
        H2 header = new H2("System");
        Paragraph information = new Paragraph("The Chatbot AI system is built using advanced artificial intelligence technologies, including natural language processing (NLP), machine learning (ML), and deep learning algorithms. This allows the system to learn from user interactions, improving its accuracy and responsiveness over time.\n" +
                "\n" +
                "Whether you need help with finding information, or simply engaging in conversation, our Chatbot AI system is here to assist you. It is available 24/7, ready to respond to your queries and provide you with personalized support.\n" +
                "\n");

        layout.add(information);
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header, layout);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.START);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");}

}
