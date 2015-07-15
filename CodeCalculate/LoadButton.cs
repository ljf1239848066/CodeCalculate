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
    public partial class LoadButton : Button
    {
        private bool isStartAnim = false;

        private String text;

        private int animImageIndex = 0;
        private Image[] animImage = new Image[] { CodeCalculate.Properties.Resources.load_1, CodeCalculate.Properties.Resources.load_2, CodeCalculate.Properties.Resources.load_3, CodeCalculate.Properties.Resources.load_4, CodeCalculate.Properties.Resources.load_5, CodeCalculate.Properties.Resources.load_6, CodeCalculate.Properties.Resources.load_7, CodeCalculate.Properties.Resources.load_8 };

        [Browsable(false)]
        public bool IsStartAnim
        {
            get { return isStartAnim; }
            set { isStartAnim = value; }
        }

        public LoadButton()
        {
            InitializeComponent();
        }

        private void LoadButton_Validated(object sender, EventArgs e)
        {
            timLoad_Tick(sender,e);
            timLoad.Stop();
        }

        private void LoadButton_TextChanged(object sender, EventArgs e)
        {
            if (this.Text != "")
            {
                text = this.Text;
            }
            timLoad.Stop();
        }

        private void timLoad_Tick(object sender, EventArgs e)
        {
            if (isStartAnim)
            {
                this.Text = "";
                this.BackgroundImage = animImage[animImageIndex];
                animImageIndex++;
                if (animImageIndex == 8)
                    animImageIndex = 0;
            }
        }

        public void startAnim()
        {
            this.Text = "";
            this.Enabled = false;
            isStartAnim = true;
            timLoad.Start();
        }

        public void stopAnim()
        {
            isStartAnim = false;
            this.BackgroundImage = null;
            this.Text = text;
            this.Enabled = true;
            timLoad.Stop();
        }

    }
}
