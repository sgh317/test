/**
 * Created by DiGua on 2015/12/2.
 */

var DIALOG = {};
DIALOG.loading = function()
{
    var h = "<div class=\"wk-loader-bg\">\n";
    h += "<div class=\"wk-loader-box\"></div>\n";
    h += "<div class=\"wk-loader la-2x\">\n";
    h += "<div></div>\n";
    h += "<div></div>\n";
    h += "<div>loading...</div>\n";
    h += "</div>\n";
    h += "</div>\n";

    $("body").prepend(h);
};
DIALOG.closeLoader = function()
{
    try {
        $(".wk-loader-bg").remove();
    } catch (e) {}
};
DIALOG.close = function()
{
    DIALOG.closeLoader();
};