package com.example.application.Views.chat;

import com.example.application.Views.MainLayout;
import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.MessageManager;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Aside;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Background;
import com.vaadin.flow.theme.lumo.LumoUtility.BoxSizing;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Flex;
import com.vaadin.flow.theme.lumo.LumoUtility.FlexDirection;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.Overflow;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.Width;
import java.util.UUID;

@PageTitle("Chat")
@Route(value = "Chat", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ChatView extends HorizontalLayout {

    /**
     * Attributes
     */
    private ChatInfo[] chats = new ChatInfo[]{new ChatInfo("Chat", 0), new ChatInfo("Coming soon", 0)};
    private ChatInfo currentChat = chats[0];
    private Tabs tabs;

    public ChatView() {
        addClassNames("chat-view", Width.FULL, Display.FLEX, Flex.AUTO);
        setSpacing(false);

        // UserInfo is used by Collaboration Engine and is used to share details
        UserInfo userInfo = new UserInfo(UUID.randomUUID().toString(), "You");

        tabs = new Tabs();
        // Iterate through the chats
        for (ChatInfo chat : chats) {
            // Listen for new messages in each chat, so we can update the "unread" count
            MessageManager mm = new MessageManager(this, userInfo, chat.getCollaborationTopic());
            mm.setMessageHandler(context -> {
                if (currentChat != chat) {
                    chat.incrementUnread();
                }
            });

            tabs.add(createTab(chat));
        }
        tabs.setOrientation(Orientation.VERTICAL);
        tabs.addClassNames(Flex.GROW, Flex.SHRINK, Overflow.HIDDEN);

        // CollaborationMessageList displays messages that are in a specific topic
        CollaborationMessageList list = new CollaborationMessageList(userInfo, currentChat.getCollaborationTopic());
        list.setSizeFull();

        // `CollaborationMessageInput is a textfield and button, to be able to submit messages.
        CollaborationMessageInput input = new CollaborationMessageInput(list);
        input.setWidthFull();

        VerticalLayout chatContainer = new VerticalLayout();
        chatContainer.addClassNames(Flex.AUTO, Overflow.HIDDEN);

        Aside side = new Aside();
        side.addClassNames(Display.FLEX, FlexDirection.COLUMN, Flex.GROW_NONE, Flex.SHRINK_NONE, Background.CONTRAST_5);
        side.setWidth("18rem");
        Header header = new Header();
        header.addClassNames(Display.FLEX, FlexDirection.ROW, Width.FULL, AlignItems.CENTER, Padding.MEDIUM,
                BoxSizing.BORDER);
        H3 channels = new H3("Channels");
        channels.addClassNames(Flex.GROW, Margin.NONE);

        // add
        header.add(channels);
        side.add(header, tabs);
        chatContainer.add(list, input);
        add(chatContainer, side);
        setSizeFull();
        expand(list);

        // Change the topic id of the chat when a new tab is selected
        tabs.addSelectedChangeListener(event -> {
            currentChat = ((ChatTab) event.getSelectedTab()).getChatInfo();
            currentChat.resetUnread();
            list.setTopic(currentChat.getCollaborationTopic());
        });
    }

    /**
     *
     * Creates a tab for side menu
     *
     * @param chat information
     * @return ChatTab
     */
    private ChatTab createTab(ChatInfo chat) {
        ChatTab tab = new ChatTab(chat);
        tab.addClassNames(JustifyContent.BETWEEN);
        Span badge = new Span();
        tab.add(new Span(chat.name), badge);
        return tab;
    }

    /**
     * Set details for mobile
     * @param attachEvent event
     */
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        Page page = attachEvent.getUI().getPage();
        page.retrieveExtendedClientDetails(details -> {
            setMobile(details.getWindowInnerWidth() < 740);
        });
        page.addBrowserWindowResizeListener(e -> {
            setMobile(e.getWidth() < 740);
        });
    }

    /**
     * Mobile orientation for viewing
     * @param mobile mobile object
     */
    private void setMobile(boolean mobile) {
        tabs.setOrientation(mobile ? Orientation.HORIZONTAL : Orientation.VERTICAL);
    }

}
