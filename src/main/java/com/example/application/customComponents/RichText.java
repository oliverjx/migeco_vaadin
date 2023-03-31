package com.example.application.customComponents;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;

@JavaScript("https://cdn.ckeditor.com/ckeditor5/36.0.1/super-build/ckeditor.js")
@CssImport("./themes/myapp/rich-text-layout.css")
public class RichText extends CustomField<String> {

    private Dialog dialog;
    private TextArea textArea;

    public RichText(Dialog dialog) {
        textArea = new TextArea();
        textArea.setVisible(true);
        this.dialog = dialog;
        Div div = new Div();
        div.setId("editor");
        add(textArea, div, footer());
        executeJs();
    }
    @Override
    protected String generateModelValue() {
        return getValue();
    }

    @Override
    protected void setPresentationValue(String s) {
        setValue(s);
    }

    @Override
    public String getValue() {
        return textArea.getValue();
    }

    private HorizontalLayout footer() {
        Button btnSave = new Button();
        Button btnCancel = new Button();
        HorizontalLayout footer = new HorizontalLayout();
        btnSave.getStyle().set("color", "white");
        btnSave.getStyle().set("background-color", "rgb(30, 185, 199)");
        btnSave.setText("Salvar");
        btnSave.setWidthFull();
        btnCancel.getStyle().set("color", "white");
        btnCancel.getStyle().set("background-color", "#E74C3C");
        btnCancel.setWidthFull();
        btnCancel.setText("Salir");
        btnCancel.addClickListener(buttonClickEvent -> dialog.close());
        footer.add(btnSave, btnCancel);
        footer.setSizeFull();
        btnSave.addClickListener(event -> {UI.getCurrent().getPage().executeJs( "" +
                " var output = document.getElementById(\"editor\").innerHTML;\n" +
                " return output;").then(String.class, this::setTextField);
        });
        return footer;
    }

    @Override
    public void setValue(String t) {
        if (t != null) {
            textArea.setValue(t);
            UI.getCurrent().getPage().executeJs( "" +
                    " var output = document.getElementById(\"editor\");\n" +
                    " output.innerHTML = "+t+";");
        }
    }

    private void setTextField(String text) {
        if(text.equals("")){
            Notification.show("Debe hacer click en el marker", 20000, Notification.Position.MIDDLE);
        }else{
            textArea.getElement().setProperty("value", text);
//            dialog.close();
        }
    }


    private void executeJs(){
        String js = """
                CKEDITOR.ClassicEditor.create(document.getElementById("editor"), {
                \t\t// https://ckeditor.com/docs/ckeditor5/latest/features/toolbar/toolbar.html#extended-toolbar-configuration-format
                \t\ttoolbar: {
                \t\t\titems: [
                \t\t\t'exportPDF','exportWord',  '|', 'findAndReplace', 'selectAll', '|',
                \t\t\t\t'heading', '|',
                \t\t\t\t'bold', 'italic', 'strikethrough', 'underline', 'code', 'subscript', 'superscript', 'removeFormat', '|',
                \t\t\t\t'bulletedList', 'numberedList', 'todoList', '|',
                \t\t\t\t'outdent', 'indent', '|',
                \t\t\t\t'undo', 'redo',
                \t\t\t\t'-',
                \t\t\t\t'fontSize', 'fontFamily', 'fontColor', 'fontBackgroundColor', 'highlight', '|',
                \t\t\t\t'alignment', '|',
                \t\t\t\t'link', 'insertImage', 'blockQuote', 'insertTable', 'mediaEmbed', 'codeBlock', 'htmlEmbed', '|',
                \t\t\t\t'specialCharacters', 'horizontalLine', 'pageBreak', '|',
                \t\t\t\t'textPartLanguage', '|',
                \t\t\t\t'sourceEditing'
                \t\t\t],
                \t\t\tshouldNotGroupWhenFull: true
                \t\t},
                \t\t// Changing the language of the interface requires loading the language file using the <script> tag.
                \t\t// language: 'es',
                \t\tlist: {
                \t\t\tproperties: {
                \t\t\t\tstyles: true,
                \t\t\t\tstartIndex: true,
                \t\t\t\treversed: true
                \t\t\t}
                \t\t},
                \t\t// https://ckeditor.com/docs/ckeditor5/latest/features/headings.html#configuration
                \t\theading: {
                \t\t\toptions: [
                \t\t\t\t{ model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
                \t\t\t\t{ model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
                \t\t\t\t{ model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
                \t\t\t\t{ model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
                \t\t\t\t{ model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
                \t\t\t\t{ model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' },
                \t\t\t\t{ model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6' }
                \t\t\t]
                \t\t},
                \t\t// https://ckeditor.com/docs/ckeditor5/latest/features/editor-placeholder.html#using-the-editor-configuration
                \t\tplaceholder: 'Welcome to CKEditor 5!',
                \t\t// https://ckeditor.com/docs/ckeditor5/latest/features/font.html#configuring-the-font-family-feature
                \t\tfontFamily: {
                \t\t\toptions: [
                \t\t\t\t'default',
                \t\t\t\t'Arial, Helvetica, sans-serif',
                \t\t\t\t'Courier New, Courier, monospace',
                \t\t\t\t'Georgia, serif',
                \t\t\t\t'Lucida Sans Unicode, Lucida Grande, sans-serif',
                \t\t\t\t'Tahoma, Geneva, sans-serif',
                \t\t\t\t'Times New Roman, Times, serif',
                \t\t\t\t'Trebuchet MS, Helvetica, sans-serif',
                \t\t\t\t'Verdana, Geneva, sans-serif'
                \t\t\t],
                \t\t\tsupportAllValues: true
                \t\t},
                \t\t// https://ckeditor.com/docs/ckeditor5/latest/features/font.html#configuring-the-font-size-feature
                \t\tfontSize: {
                \t\t\toptions: [ 10, 12, 14, 'default', 18, 20, 22 ],
                \t\t\tsupportAllValues: true
                \t\t},
                \t\t// Be careful with the setting below. It instructs CKEditor to accept ALL HTML markup.
                \t\t// https://ckeditor.com/docs/ckeditor5/latest/features/general-html-support.html#enabling-all-html-features
                \t\thtmlSupport: {
                \t\t\tallow: [
                \t\t\t\t{
                \t\t\t\t\tname: /.*/,
                \t\t\t\t\tattributes: true,
                \t\t\t\t\tclasses: true,
                \t\t\t\t\tstyles: true
                \t\t\t\t}
                \t\t\t]
                \t\t},
                \t\t// Be careful with enabling previews
                \t\t// https://ckeditor.com/docs/ckeditor5/latest/features/html-embed.html#content-previews
                \t\thtmlEmbed: {
                \t\t\tshowPreviews: true
                \t\t},
                \t\t// https://ckeditor.com/docs/ckeditor5/latest/features/link.html#custom-link-attributes-decorators
                \t\tlink: {
                \t\t\tdecorators: {
                \t\t\t\taddTargetToExternalLinks: true,
                \t\t\t\tdefaultProtocol: 'https://',
                \t\t\t\ttoggleDownloadable: {
                \t\t\t\t\tmode: 'manual',
                \t\t\t\t\tlabel: 'Downloadable',
                \t\t\t\t\tattributes: {
                \t\t\t\t\t\tdownload: 'file'
                \t\t\t\t\t}
                \t\t\t\t}
                \t\t\t}
                \t\t},
                \t\t// https://ckeditor.com/docs/ckeditor5/latest/features/mentions.html#configuration
                \t\tmention: {
                \t\t\tfeeds: [
                \t\t\t\t{
                \t\t\t\t\tmarker: '@',
                \t\t\t\t\tfeed: [
                \t\t\t\t\t\t'@apple', '@bears', '@brownie', '@cake', '@cake', '@candy', '@canes', '@chocolate', '@cookie', '@cotton', '@cream',
                \t\t\t\t\t\t'@cupcake', '@danish', '@donut', '@dragée', '@fruitcake', '@gingerbread', '@gummi', '@ice', '@jelly-o',
                \t\t\t\t\t\t'@liquorice', '@macaroon', '@marzipan', '@oat', '@pie', '@plum', '@pudding', '@sesame', '@snaps', '@soufflé',
                \t\t\t\t\t\t'@sugar', '@sweet', '@topping', '@wafer'
                \t\t\t\t\t],
                \t\t\t\t\tminimumCharacters: 1
                \t\t\t\t}
                \t\t\t]
                \t\t},
                \t\t// The "super-build" contains more premium features that require additional configuration, disable them below.
                \t\t// Do not turn them on unless you read the documentation and know how to configure them and setup the editor.
                \t\tremovePlugins: [
                \t\t\t// These two are commercial, but you can try them out without registering to a trial.
                \t\t\t// 'ExportPdf',
                \t\t\t// 'ExportWord',
                \t\t\t'CKBox',
                \t\t\t'CKFinder',
                \t\t\t'EasyImage',
                \t\t\t// This sample uses the Base64UploadAdapter to handle image uploads as it requires no configuration.
                \t\t\t// https://ckeditor.com/docs/ckeditor5/latest/features/images/image-upload/base64-upload-adapter.html
                \t\t\t// Storing images as Base64 is usually a very bad idea.
                \t\t\t// Replace it on production website with other solutions:
                \t\t\t// https://ckeditor.com/docs/ckeditor5/latest/features/images/image-upload/image-upload.html
                \t\t\t// 'Base64UploadAdapter',
                \t\t\t'RealTimeCollaborativeComments',
                \t\t\t'RealTimeCollaborativeTrackChanges',
                \t\t\t'RealTimeCollaborativeRevisionHistory',
                \t\t\t'PresenceList',
                \t\t\t'Comments',
                \t\t\t'TrackChanges',
                \t\t\t'TrackChangesData',
                \t\t\t'RevisionHistory',
                \t\t\t'Pagination',
                \t\t\t'WProofreader',
                \t\t\t// Careful, with the Mathtype plugin CKEditor will not load when loading this sample
                \t\t\t// from a local file system (file://) - load this site via HTTP server if you enable MathType
                \t\t\t'MathType'
                \t\t]
                \t});""";
        UI.getCurrent().getPage().executeJs(js);
    }

}
