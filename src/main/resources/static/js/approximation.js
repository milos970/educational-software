/* $("document").ready(function()
{
    var i=1;
   $("#add_row").click(function(){b=i-1;
    $('#addr'+i).html($('#addr'+b).html()).find('td:first-child').html(i+1);
    $('#tab_logic').append('<tr id="addr'+(i+1)+'"></tr>');
    i++; 
});
   $("#delete_row").click(function(){
       if(i>1){
       $("#addr"+(i-1)).html('');
       i--;
       }
   });

}); */

function validate() 
{
    const regex = /\[\((-?\d+,\s*-?\d+)(?:(?:(?!\1)[^\n])*,\s*(-?\d+,\s*-?\d+)(?:(?!\1|\3)[^\n])*)*\)\]/;
    var x = document.getElementById("data").value;
}

function parse() 
{
    var eq = document.getElementById("equation").value;
    let matches = eq.match(/-?\d+(\.\d+)?/g);

   // matches.forEach((element) => console.log(element));

    let data = [];
    let j = 0;
    for (let i = 0; i < matches.length; i += 2) 
    {
        data[j] = [2];

        data[j][0] = matches[i];

        data[j][1] = matches[i + 1];

        ++j;
    }

    /* for(var i = 0; i < data.length; i++) {
        for(var z = 0; z < 2; z++) {
          console.log(data[i][z]);
        }
      } */

    return data;
}


function spline() 
{
    let data = parse();

    let dk = [];
    
    for (let i = 0; i < data.length - 1; ++i) 
    {
        dk[i] = (data[i + 1][1] - data[i][1]) / (data[i + 1][0] - data[i][0]);
    }

    

    let Sx = [data.length - 1];

    for (let i = 0; i < data.length - 1; ++i) 
    {
        Sx[i] = "";
        Sx[i] += data[i][1];
        if (dk[i] > 0) 
        {
            Sx[i] += "+";
        } 
        Sx[i] += dk[i];
        
        if (data[i][0] > 0) 
        {
            Sx[i] += "(x-" + data[i][0]+ ")";
        } else 
        {
            Sx[i] += "(x+" + ((-1) * data[i][0]) + ")";
        }
    }

     for(var i = 0; i < Sx.length; i++) 
     {
          console.log(Sx[i]);
    }
    

}


function langrange() 
{
    var eq = document.getElementById("data").value;
    let matches = eq.match(/-?\d+(\.\d+)?/g);

    matches.forEach((element) => console.log(element));
    arr = matches;
    let equation = "";
    let first = true;
    
    for (let i = 0; i < arr.length; i+=2) 
    {
        let part = "";
        let menovatel = 1;
        for (let k = 0; k < arr.length; k+=2) 
        {
            
                if (i == k) 
                {
                    continue;
                }

                
                if (arr[k] > 0) 
                {
                    part += "(x - " + arr[k] + ")";
                } else 
                {
                    part += "(x + " + ((-1) * (arr[k])) + ")";
                }
                

                menovatel *= (arr[i] - arr[k]);
            
        }

        menovatel = 1/ menovatel;

        

        if (first) 
        {
            part = (arr[i + 1] * menovatel) + part;
            equation += part;
            first = false;
        } else 
        {
            if (arr[i + 1] * menovatel > 0) 
            {
                part = "+" + (arr[i + 1] * menovatel) + part;
            } else 
            {
                part = (arr[i + 1] * menovatel) + part;
            }

            equation += part;
        }


    }

    var parsedEquation = math.parse(equation);
    var next = math.evaluate(parsedEquation.toString(), { x: 1 });
        alert(next);


}



