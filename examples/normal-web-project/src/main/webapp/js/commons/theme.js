$(function(){
    var themes = [
        {value:'default',text:'Default',group:'Base'},
        {value:'gray',text:'Gray',group:'Base'},
        {value:'metro',text:'Metro',group:'Base'},
        {value:'bootstrap',text:'Bootstrap',group:'Base'},
        {value:'black',text:'Black',group:'Base'},
        {value:'metro-blue',text:'Metro Blue',group:'Metro'},
        {value:'metro-gray',text:'Metro Gray',group:'Metro'},
        {value:'metro-green',text:'Metro Green',group:'Metro'},
        {value:'metro-orange',text:'Metro Orange',group:'Metro'},
        {value:'metro-red',text:'Metro Red',group:'Metro'},
    ];
    $('#cb-theme').combobox({
        groupField:'group',
        data: themes,
        editable:false,
        panelHeight:'auto',
        onChange:onChangeTheme,
        onLoadSuccess:function(){
            //$(this).combobox('setValue', 'metro-blue');
        }
    });
    onChangeTheme('metro-blue');
});


function onChangeTheme(theme){
    var links = document.getElementsByTagName("link");
    var link = links[0];
    link.href = urls.easyuiUrl + '/themes/' + theme + '/easyui.css';
    $.cookie('theme', theme, {
        expires : 30
    })
}
