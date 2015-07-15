using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace CodeCalculate
{
    public partial class CustomListView : ListView
    {
        public CustomListView()
        {
                SetStyle(ControlStyles.DoubleBuffer |
                                  ControlStyles.OptimizedDoubleBuffer |
                                  ControlStyles.AllPaintingInWmPaint,
                                  true);
                UpdateStyles();
        }
    }
}
