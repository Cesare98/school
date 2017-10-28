
var utile = new Array();

$(document).ready(function ()
{

    var num = 0;
    $("#btnCasella").click(function () {
        num = $("#generaCaselle").val();
        var i = new Number();
        for (i = 0; i < num; i++)
        {

            var br = document.createElement("br");
            var box = document.createElement("input");

            randNum = (Math.random() * 100);

            box.value = 0;
            box.value = randNum;
            box.setAttribute("id", i);
            box.setAttribute("value", randNum);
            box.setAttribute("disabled", true);

            utile[i] = box.value;

            $("#caselle").append(box);

        }

        $("#btnLeft").click(function ()
        {
            var k = new Number();
            var numero = 0;
            for (k = num; k > 0; k--)
            {

                if (k === num)
                {
                    utile[k] = utile[0];
                }

                if (k > 0 && k !== num)
                {
                    numero = utile[(k - 1)].value;
                    utile[(k - 1)].value = utile[k].value;
                    utile[k].value = numero;
                }

            }

            $("input").each(function (j, box)
            {
                if (j !== 0) {
                    box.value = utile[j];
                    j += 1;
                }
            });
        });


        $("#btnRight").click(function ()
        {
            var k = new Number();
            var temp = 0;
            var primo = 0;
            var ultimo = utile[num-2];

            for (k = num - 1; k >= 0; k--)
            {

                if (k === num - 1)
                {
                    primo = utile[num - 1];
                }

                if (k !== num - 1 && (k - 1) >= 0) {
                    temp = utile[k];
                    utile[k] = utile[(k - 1)];
                    utile[(k - 1)] = temp;
                    console.log(ultimo);
                }
                if (k === 0)
                {
                    utile[0] = primo;
                    utile[num - 1] = ultimo;
                }


            }

            $("input").each(function (j, box)
            {
                if (j !== 0) {
                    box.value = utile[j - 1];
                    j += 1;
                }
            });
        });

        $("#btnSort").click(function () {
            utile.sort();
            $("input").each(function (j, box)
            {
                if (j !== 0) {
                    box.value = utile[j - 1];
                    j += 1;
                }
            });
        });
    });

});

  