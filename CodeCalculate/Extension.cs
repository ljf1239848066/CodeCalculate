using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Text.RegularExpressions;

namespace CodeCalculate
{
    public class Extension
    {
        private static String FILE_PATH;
        private String file_name;
        private List<List<String>> extensions;

        public List<List<String>> Extensions
        {
            get { return extensions; }
            set { extensions = value; }
        }

        public Boolean ReadExtensions(String path)
        {
            if (File.Exists(path))
            {
                FileStream fs = File.OpenRead(path);
                StreamReader sr = new StreamReader(fs);
                String str = sr.ReadToEnd();
                sr.Close();
                fs.Close();
                String[] strs = Regex.Split(str, "***", RegexOptions.IgnoreCase);
                extensions = new List<List<string>>();
                int length = strs.Length;
                for (int i = 0; i < length; i++)
                {
                    String[] strss = strs[i].Split('\n');
                    int tempLen = strss.Length;
                    List<String> tempList = new List<string>();
                    for (int j = 0; j < tempLen; j++)
                    {
                        tempList.Add(strss[j]);
                    }
                    extensions.Add(tempList);
                }
                if (extensions != null)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        public void saveExtensions(String path)
        {
            FileStream fs = new FileStream(path, FileMode.Create, FileAccess.ReadWrite);
            StreamWriter sw = new StreamWriter(fs);
            int length = extensions.Count;
            for (int i = 0; i < length;i++)
            {
                int len = extensions[i].Count;
                for (int j = 0; j < len;j++ )
                {
                    String tempStr = string.Format("{0}\n", extensions[i][j]);
                    sw.Write(tempStr, true);
                }
                if (i == length - 1)
                {
                    String tempStr = string.Format("{0}\n", "***");
                    sw.Write(tempStr, true);
                }
            }
            sw.Close();
            fs.Close();
        }
    }
}
