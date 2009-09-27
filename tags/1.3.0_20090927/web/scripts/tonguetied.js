function toggleSection(expression, elementId) {
    if (expression)
    {
        document.getElementById(elementId).style.display = "block";
        document.getElementById(elementId).style.visibility = "visible";
    }
    else
    {
        document.getElementById(elementId).style.display = "none";
        document.getElementById(elementId).style.visibility = "hidden";
    }
}
